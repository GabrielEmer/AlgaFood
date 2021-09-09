package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private ProdutoModelAssembler assembler;

    @Autowired
    private ProdutoInputDisassembler disassembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        return assembler.toCollectionModel(cadastroRestaurante.buscar(restauranteId).getProdutos());
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return assembler.toModel(cadastroProduto.buscar(restauranteId, produtoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
        Produto produto = disassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        return assembler.toModel(cadastroProduto.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produto = cadastroProduto.buscar(restauranteId, produtoId);
        disassembler.copyToDomainObject(produtoInput, produto);
        return assembler.toModel(cadastroProduto.salvar(produto));
    }
}