package com.algaworks.algafood.domain.exception;

public class EntidadeEmUsoException extends NegocioException {

    public static final Long serialVersionUID = 1L;

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
}
