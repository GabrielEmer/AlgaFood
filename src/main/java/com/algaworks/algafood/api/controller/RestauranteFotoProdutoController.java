package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.disassembler.FotoProdutoInputDisassembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private FotoProdutoInputDisassembler disassembler;

    @Autowired
    private FotoProdutoModelAssembler assembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                      @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        return assembler.toModel(catalogoFotoProduto.salvar(
                disassembler.toDomainModel(restauranteId, produtoId, fotoProdutoInput),
                fotoProdutoInput.getArquivo().getInputStream())
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return assembler.toModel(catalogoFotoProduto.buscarFoto(restauranteId, produtoId));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> buscarArquivoFoto(@PathVariable Long restauranteId,
            @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            List<MediaType> mediaTypes = MediaType.parseMediaTypes(acceptHeader);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(catalogoFotoProduto.buscarArquivoFoto(restauranteId, produtoId, mediaTypes)));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProduto.removerFoto(restauranteId, produtoId);
    }
}