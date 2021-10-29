package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    default String gerarNomeArquivo(String nomeOriginal) {
        return String.format("%s_%s", UUID.randomUUID(), nomeOriginal);
    }

    @Getter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;
    }
}
