package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService storageService;

    private String nomeArquivoAntigo = null;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        deletarFotoSeExistirUmCadastro(foto);
        foto.setNomeArquivo(storageService.gerarNomeArquivo(foto.getNomeArquivo()));
        FotoProduto fotoProduto = produtoRepository.save(foto);
        produtoRepository.flush();
        substituirFoto(foto, dadosArquivo);
        return fotoProduto;
    }

    private void deletarFotoSeExistirUmCadastro(FotoProduto foto) {
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(
                foto.getProduto().getRestaurante().getId(),
                foto.getProduto().getId()
        );

        fotoExistente.ifPresent(fotoProduto -> {
            this.nomeArquivoAntigo = fotoProduto.getNomeArquivo();
            produtoRepository.delete(fotoProduto);
        });
    }

    private void substituirFoto(FotoProduto foto, InputStream dadosArquivo) {
        storageService.substituir(
                nomeArquivoAntigo,
                FotoStorageService.NovaFoto.builder()
                        .nomeArquivo(foto.getNomeArquivo())
                        .inputStream(dadosArquivo)
                        .build()
        );
    }
}
