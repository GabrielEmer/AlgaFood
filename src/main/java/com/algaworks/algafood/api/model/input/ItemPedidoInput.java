package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemPedidoInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long produtoId;

    @ApiModelProperty(example = "2", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;
}
