package com.algaworks.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), processarTemplate(mensagem));
    }
}
