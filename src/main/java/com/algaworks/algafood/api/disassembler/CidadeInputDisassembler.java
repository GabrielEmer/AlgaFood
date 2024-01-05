package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
public class CidadeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager manager;

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade){
        manager.detach(cidade.getEstado());
        modelMapper.map(cidadeInput, cidade);
    }
}
