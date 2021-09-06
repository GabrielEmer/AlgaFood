package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Grupo;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends CustomJpaRepository<Grupo, Long> {
}
