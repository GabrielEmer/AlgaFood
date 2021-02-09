package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @Autowired
    private EntityManager manager;

    @Override
    public List<Estado> listar() {
        return manager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscar(String id) {
        return manager.find(Estado.class, id);
    }

    @Override
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    public void remover(Estado estado) {
        manager.remove(estado);
    }
}
