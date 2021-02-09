package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRespository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRespository {
    @Override
    public List<Cidade> listar() {
        return null;
    }

    @Override
    public Cidade buscar(Long id) {
        return null;
    }

    @Override
    public Cidade salvar(Cidade cidade) {
        return null;
    }

    @Override
    public void remover(Cidade cidade) {

    }
}
