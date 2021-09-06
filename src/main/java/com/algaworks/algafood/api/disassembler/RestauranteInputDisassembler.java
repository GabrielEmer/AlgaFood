package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager manager;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        manager.detach(restaurante.getCozinha());

        if (restaurante.getEndereco() != null){
            //restaurante.getEndereco().setCidade(new Cidade());
            Hibernate.initialize(restaurante.getEndereco().getCidade());
            manager.detach(restaurante.getEndereco().getCidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }
}
