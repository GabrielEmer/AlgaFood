package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    REQUISICAO_INVALIDA("/requisicao-invalida", "Requisição inválida."),
    RECURSO_NAO_ENCONTRADA("/recurso-nao-encontrada", "Recurso não encontrada."),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio."),
    PARAMETRO_INVALIDO("/parametro-invalido","Parametro inválido."),
    ERRO_DE_SISTEMA("/erro-sistema", "Erro de sistema."),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos.");

    private String uri;
    private String title;

    ProblemType(String path, String title) {
        this.uri = "https://gabrielemer.com.br" + path;
        this.title = title;
    }
}
