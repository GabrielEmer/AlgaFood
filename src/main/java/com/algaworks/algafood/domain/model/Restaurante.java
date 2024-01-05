package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.TRUE;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
      joinColumns = @JoinColumn(name = "restaurante_id"),
      inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel ",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
        setAtivo(true);
    }
    public void inativar() {
        setAtivo(false);
    }
    public void abrir() {
        setAberto(true);
    }
    public void fechar() {
        setAberto(false);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento) {
        formasPagamento.remove(formaPagamento);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        formasPagamento.add(formaPagamento);
    }

    public void adicionarResponsavel(Usuario usuario) {
        responsaveis.add(usuario);
    }

    public void removerResponsavel(Usuario usuario) {
        responsaveis.remove(usuario);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento.contains(formaPagamento);
    }
}