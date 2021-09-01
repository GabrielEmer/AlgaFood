package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {

    @Autowired
    CozinhaModelAssembler cozinhaModelAssembler;

    public RestauranteModel toModel(Restaurante restaurante){
        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxafrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModelAssembler.toModel(restaurante.getCozinha()));

        return restauranteModel;
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                    .map(this::toModel)
                    .collect(Collectors.toList());
    }
}
