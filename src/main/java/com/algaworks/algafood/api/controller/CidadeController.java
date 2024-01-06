package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CidadeModel> listar () {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel buscar (@PathVariable Long cidadeId) {
        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.buscar(cidadeId));
        cidadeModel.add(linkTo(CidadeController.class)
                .slash(cidadeModel.getId()).withSelfRel());

//        cidadeModel.add(Link.of("localhost:8080/cidades/" + cidadeModel.getId()));
        cidadeModel.add(linkTo(CidadeController.class)
                .withRel("cidades"));

//        cidadeModel.add(Link.of("localhost:8080/cidades", "cidades"));

        cidadeModel.getEstado().add(linkTo(EstadoController.class)
                .slash(cidadeModel.getEstado().getId()).withSelfRel());
//        cidadeModel.getEstado().add(Link.of("localhost:8080/estados/" + cidadeModel.getEstado().getId()));
        return cidadeModel;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidade) {
        CidadeModel cidadeModel = salvarCidade(cidadeInputDisassembler.toDomainObject(cidade));

        ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
        return cidadeModel;
    }

    @PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId){
        cadastroCidade.excluir(cidadeId);
    }
}
