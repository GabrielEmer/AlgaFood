package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.disassembler.FotoProdutoInputDisassembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private FotoProdutoInputDisassembler disassembler;

    @Autowired
    private FotoProdutoModelAssembler assembler;

    @PutMapping
    public FotoProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                      @Valid FotoProdutoInput fotoProdutoInput) {
        return assembler.toModel(catalogoFotoProduto.salvar(
                disassembler.toDomainModel(restauranteId, produtoId, fotoProdutoInput)));
    }


}