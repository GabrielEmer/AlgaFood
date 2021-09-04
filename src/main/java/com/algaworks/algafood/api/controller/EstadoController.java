package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.disassembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoModel> listar() {
        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoModel buscar(@PathVariable Long estadoId) {
        return estadoModelAssembler.toModel(cadastroEstado.buscar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estado) {
        return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoInputDisassembler.toDomainObject(estado)));
    }

    @PutMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel atualizar(@PathVariable("estadoId") Long estadoId, @RequestBody @Valid EstadoInput estado) {
        Estado estadoAtual = cadastroEstado.buscar(estadoId);
        estadoInputDisassembler.copyToDomainObject(estado, estadoAtual);
        return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estadoId") Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }
}
