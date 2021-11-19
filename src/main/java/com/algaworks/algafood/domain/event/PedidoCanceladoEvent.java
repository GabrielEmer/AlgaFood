package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PedidoCanceladoEvent {
    private Pedido pedido;
}
