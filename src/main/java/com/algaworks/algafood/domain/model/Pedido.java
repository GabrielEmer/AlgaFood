package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Pedido extends AbstractAggregateRoot<Pedido> {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codigo;

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
  @ToString.Exclude
  private FormaPagamento formaPagamento;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Restaurante restaurante;

  @ManyToOne
  @JoinColumn(name = "usuario_cliente_id", nullable = false)
  private Usuario cliente;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
  @ToString.Exclude
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

  public void confirmar() {
    setStatus(StatusPedido.CONFIRMADO);
    setDataConfirmacao(OffsetDateTime.now());
    registerEvent(new PedidoConfirmadoEvent(this));
  }

  public void cancelar() {
    setStatus(StatusPedido.CANCELADO);
    setDataCancelamento(OffsetDateTime.now());
    registerEvent(new PedidoCanceladoEvent(this));
  }

  public void entregar() {
    setStatus(StatusPedido.ENTREGUE);
    setDataEntrega(OffsetDateTime.now());
  }

  private void setStatus(StatusPedido status) {
    if (!getStatus().podeAlterarPara(status))
      throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s.",
            getId(), getStatus().getDescricao(), status.getDescricao()));

    this.status = status;
  }

  @PrePersist
  private void gerarCodigo() {
    setCodigo(UUID.randomUUID().toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Pedido pedido = (Pedido) o;

    return Objects.equals(id, pedido.id);
  }

  @Override
  public int hashCode() {
    return 1729204124;
  }
}
