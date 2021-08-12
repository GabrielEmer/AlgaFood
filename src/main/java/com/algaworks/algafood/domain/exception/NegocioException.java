package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException{

    public static final Long serialVersionUID = 1L;

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
