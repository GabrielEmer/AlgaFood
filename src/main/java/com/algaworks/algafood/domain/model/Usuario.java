package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String senha;

  @CreationTimestamp
  @Column(nullable = false, columnDefinition = "dateTime")
  private OffsetDateTime dataCadastro;

  @ManyToMany
  @JoinTable(name = "usuario_grupo",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "grupo_id"))
  private Set<Grupo> grupos = new HashSet<>();

  public boolean verificarSenhaValida(String senhaAtual) {
    return getSenha().equals(senhaAtual);
  }

  public void adicionarGrupo(Grupo grupo) {
    grupos.add(grupo);
  }

  public void removerGrupo(Grupo grupo) {
    grupos.remove(grupo);
  }
}
