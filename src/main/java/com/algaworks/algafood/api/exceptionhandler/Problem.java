package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    @ApiModelProperty(example = "404", position = 1)
    private Integer status;
    @ApiModelProperty(example = "https://gabrielemer.com.br/recurso-nao-encontrada", position = 5)
    private String type;
    @ApiModelProperty(example = "Recurso não encontrado", position = 10)
    private String title;
    @ApiModelProperty(example = "Não existe um cadastro de Pedido com código x", position = 15)
    private String detail;
    @ApiModelProperty(example = "Não existe um cadastro de Pedido com código x", position = 20)
    private String userMessage;
    @ApiModelProperty(example = "2022-01-29T17:34:04.3022987Z", position = 25)
    private OffsetDateTime timestamp;
    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro", position = 30)
    private List<Field> fields;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Field {

        @ApiModelProperty(example = "codigo")
        private String name;
        @ApiModelProperty(example = "codigo desconhecido")
        private String userMessage;
    }
}
