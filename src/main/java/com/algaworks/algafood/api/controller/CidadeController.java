package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeModel> listar () {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @ApiOperation("Busca uma cidade por ID")
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar (@PathVariable Long cidadeId) {
        return cidadeModelAssembler.toModel(cadastroCidade.buscar(cidadeId));
    }

    @ApiOperation("Adiciona uma nova cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidade) {
        return salvarCidade(cidadeInputDisassembler.toDomainObject(cidade));
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody @Valid CidadeInput cidade) {
        Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);
        cidadeInputDisassembler.copyToDomainObject(cidade, cidadeAtual);
        return salvarCidade(cidadeAtual);
    }

    private CidadeModel salvarCidade(@RequestBody Cidade cidade) {
        try {
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade por ID")
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId){
        cadastroCidade.excluir(cidadeId);
    }
}
