package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String nome;

  @ManyToMany
  @JoinTable(name = "grupo_permissao",
    joinColumns = @JoinColumn(name = "grupo_id"),
    inverseJoinColumns = @JoinColumn(name = "permissao_id"))
  private Set<Permissao> permissoes = new HashSet<>();

  public void adicionarPermissao(Permissao permissao) {
    permissoes.add(permissao);
  }

  public void removerPermissao(Permissao permissao) {
    permissoes.remove(permissao);
  }
}
