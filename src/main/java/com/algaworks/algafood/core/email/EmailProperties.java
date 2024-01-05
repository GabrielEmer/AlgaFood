package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@ConfigurationProperties("algafood.email")
@Component
public class EmailProperties {

    @NotNull
    private String remetente;

    @NotNull
    private TipoEnvio tipoEnvio = TipoEnvio.FAKE;

    private Sandbox sandbox = new Sandbox();

    public enum TipoEnvio {
        FAKE, SMTP, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {
        private String destinatario;
    }
}
