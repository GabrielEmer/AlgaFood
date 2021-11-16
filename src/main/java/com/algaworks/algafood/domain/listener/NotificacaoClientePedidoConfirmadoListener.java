package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    EnvioEmailService envioEmail;

    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        envioEmail.enviar(EnvioEmailService.Mensagem.builder()
                .assunto(event.getPedido().getRestaurante().getNome() + " - Pedido confirmado.")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", event.getPedido())
                .destinatario(event.getPedido().getCliente().getEmail())
                .build());
    }
}
