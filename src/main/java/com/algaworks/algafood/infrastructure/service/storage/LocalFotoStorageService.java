package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            return Files.newInputStream(getArquivoPath(nomeArquivo));
        } catch (IOException e) {
            throw new StorageException(String.format("Não foi possível recuperar o arquivo %s", nomeArquivo), e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            FileCopyUtils.copy(novaFoto.getInputStream(),
                    Files.newOutputStream(getArquivoPath(novaFoto.getNomeArquivo())));
        } catch (IOException e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Files.deleteIfExists(getArquivoPath(nomeArquivo));
        } catch (IOException e) {
            throw new StorageException(String.format("Não foi possível excluir o arquivo %s", nomeArquivo), e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
