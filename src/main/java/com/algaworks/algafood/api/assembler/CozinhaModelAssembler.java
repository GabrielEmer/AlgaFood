package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler {
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(cozinha.getId());
        cozinhaModel.setNome(cozinha.getNome());
        return cozinhaModel;
    }
}
