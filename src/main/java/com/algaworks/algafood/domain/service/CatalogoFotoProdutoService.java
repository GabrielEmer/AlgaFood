package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.InputStream;
import java.util.List;
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

    public FotoProduto buscarFoto(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(produtoId, restauranteId));
    }

    public InputStream buscarArquivoFoto(Long restauranteId, Long produtoId, List<MediaType> mediaTypes) throws HttpMediaTypeNotAcceptableException {
        FotoProduto fotoProduto = buscarFoto(restauranteId, produtoId);
        verificarCompatibilidadeMediaType(MediaType.parseMediaType(fotoProduto.getContentType()), mediaTypes);
        return storageService.recuperar(fotoProduto.getNomeArquivo());
    }

    private void verificarCompatibilidadeMediaType(MediaType contentType, List<MediaType> mediaTypes) throws HttpMediaTypeNotAcceptableException {
        if (mediaTypes.stream().noneMatch(aceita -> aceita.isCompatibleWith(contentType)))
            throw new HttpMediaTypeNotAcceptableException(mediaTypes);
    }
}
