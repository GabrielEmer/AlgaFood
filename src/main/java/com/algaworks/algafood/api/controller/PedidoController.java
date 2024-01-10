package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler assembler;

    @Autowired
    private PedidoResumoModelAssembler resumoAssembler;

    @Autowired
    private PedidoInputDisassembler disassembler;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter,
                                                   @PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), traduzirPageable(pageable));
        return pagedResourcesAssembler
                .toModel(new PageImpl<>(pedidoPage.getContent(), pageable, pedidoPage.getTotalElements()), resumoAssembler);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return assembler.toModel(emissaoPedido.buscar(codigoPedido));
    }

    @PostMapping
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = disassembler.toDomainObject(pedidoInput);

            //TODO pegar usuário autenticado
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            return assembler.toModel(emissaoPedido.emitir(pedido));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "valorTotal", "ValorTotal",
                "cliente.nome", "cliente.nome",
                "dataCriacao", "dataCriacao"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
