package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @EventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        envioEmail.enviar(EnvioEmailService.Mensagem.builder()
                .assunto(event.getPedido().getRestaurante().getNome() + " - Pedido cancelado.")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", event.getPedido())
                .destinatario(event.getPedido().getCliente().getEmail())
                .build());
    }
}
