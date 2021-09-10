package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(name = "produto_id", nullable = false)
  private Produto produto;

  private Integer quantidade;
  private BigDecimal precoUnitario;
  private BigDecimal precoTotal;
  private String observacao;

  public void calcularPrecoTotal() {
    setPrecoTotal(precoUnitario.multiply(BigDecimal.valueOf(quantidade)));
  }
}
