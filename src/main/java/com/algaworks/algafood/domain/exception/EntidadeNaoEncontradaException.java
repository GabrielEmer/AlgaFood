package com.algaworks.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException{// extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

//    public EntidadeNaoEncontradaException(HttpStatus status, String reason) {
//        super(status, reason);
//    }

    public EntidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
