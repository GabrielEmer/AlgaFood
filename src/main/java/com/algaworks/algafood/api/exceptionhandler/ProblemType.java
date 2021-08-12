package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada."),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio.");

    private String uri;
    private String title;

    ProblemType(String path, String title) {
        this.uri = "https://gabrielemer.com.br" + path;
        this.title = title;
    }
}
