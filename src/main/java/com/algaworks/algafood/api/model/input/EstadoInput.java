package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoInput {

    @ApiModelProperty(example = "São Paulo", required = true)
    @NotBlank
    private String nome;
}
