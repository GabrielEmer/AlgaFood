package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.disassembler.FotoProdutoInputDisassembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

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

    @GetMapping
    public FotoProdutoModel buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return assembler.toModel(catalogoFotoProduto.buscarFoto(restauranteId, produtoId));
    }

}