package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoInputDisassembler {

    @Autowired
    private CadastroProdutoService cadastroProduto;

    public FotoProduto toDomainModel(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput) {
        FotoProduto fotoProduto = new FotoProduto();

        fotoProduto.setProduto(cadastroProduto.buscar(restauranteId, produtoId));
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
        fotoProduto.setContentType(fotoProdutoInput.getArquivo().getContentType());
        fotoProduto.setTamanho(fotoProdutoInput.getArquivo().getSize());

        return fotoProduto;
    }
}
