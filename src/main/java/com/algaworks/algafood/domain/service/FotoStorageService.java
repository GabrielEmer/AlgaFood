package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);
    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return String.format("%s_%s", UUID.randomUUID(), nomeOriginal);
    }

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);
        if (nomeArquivoAntigo != null)
            this.remover(nomeArquivoAntigo);
    };

    @Getter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;
    }
}
