package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInput {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String senhaNova;
}
