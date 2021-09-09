package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto buscar(Long restaurenteId, Long produtoId) {
        return produtoRepository.findById(restaurenteId, produtoId).orElseThrow(
                () -> new ProdutoNaoEncontradoException(restaurenteId, produtoId)
        );
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
