package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

  CRIADO("Criado"),
  CONFIRMADO("Confirmado", CRIADO),
  ENTREGUE("Entregue", CONFIRMADO),
  CANCELADO("Cancelado", CRIADO);

  private final String descricao;
  private final List<StatusPedido> statusAnteriores;

  StatusPedido(String descricao, StatusPedido... statusAnteriores) {
    this.descricao = descricao;
    this.statusAnteriores = Arrays.asList(statusAnteriores);
  }

  public String getDescricao() {
    return descricao;
  }

  public boolean podeAlterarPara(StatusPedido novoStatus) {
    return novoStatus.statusAnteriores.contains(this);
  }
}
