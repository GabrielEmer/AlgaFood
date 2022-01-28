package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "São Paulo")
    private String nome;
}
