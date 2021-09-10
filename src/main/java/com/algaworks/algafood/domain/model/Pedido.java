package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal subtotal;
  private BigDecimal taxaFrete;
  private BigDecimal valorTotal;

  @Embedded
  private Endereco enderecoEntrega;

  @Enumerated(EnumType.STRING)
  private StatusPedido status = StatusPedido.CRIADO;

  @CreationTimestamp
  private OffsetDateTime dataCriacao;

  private OffsetDateTime dataConfirmacao;
  private OffsetDateTime dataCancelamento;
  private OffsetDateTime dataEntrega;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private FormaPagamento formaPagamento;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Restaurante restaurante;

  @ManyToOne
  @JoinColumn(name = "usuario_cliente_id", nullable = false)
  private Usuario cliente;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
  private List<ItemPedido> itens = new ArrayList<>();

  public void calcularValorTotal() {
    getItens().forEach(ItemPedido::calcularPrecoTotal);
    
    this.subtotal = getItens().stream()
            .map(ItemPedido::getPrecoTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    this.valorTotal = this.subtotal.add(this.taxaFrete);
  }

  public void adicionarPedidoAosItens() {
    getItens().forEach(item -> item.setPedido(this));
  }
}
