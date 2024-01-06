package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;
    @ApiModelProperty(example = "Uberlândia")
    private String nome;
    private EstadoModel estado;
}
