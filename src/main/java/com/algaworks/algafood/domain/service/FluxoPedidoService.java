package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private EnvioEmailService envioEmail;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscar(codigoPedido);
        pedido.confirmar();

        envioEmail.enviar(EnvioEmailService.Mensagem.builder()
                        .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado.")
                        .corpo(String.format("O pedido de código <strong>%s</strong> foi confirmado.", pedido.getCodigo()))
                        .destinatario(pedido.getCliente().getEmail())
                .build());
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscar(codigoPedido);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscar(codigoPedido);
        pedido.entregar();
    }
}
