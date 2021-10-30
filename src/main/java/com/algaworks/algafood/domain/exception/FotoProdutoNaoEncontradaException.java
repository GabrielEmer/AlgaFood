package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public static final Long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de foto para o produto %d do restaurante %d", produtoId, restauranteId));
    }
}
