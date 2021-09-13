package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public static final Long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("Não existe um cadastro de Pedido com código %s", codigoPedido));
    }
}
