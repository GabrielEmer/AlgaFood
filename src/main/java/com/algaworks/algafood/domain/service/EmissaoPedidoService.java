package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    public Pedido buscar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(
                () -> new PedidoNaoEncontradoException(pedidoId)
        );
    }

    public Pedido emitir(Pedido pedido) {
        adicionarEntidadesAoProduto(pedido);
        adicionarProdutosNoItem(pedido.getItens(), pedido.getRestaurante().getId());
        calcularValorTotalPedido(pedido);
        pedido.adicionarPedidoAosItens();
        return pedidoRepository.save(pedido);
    }

    private void calcularValorTotalPedido(Pedido pedido) {
        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
    }

    private void adicionarEntidadesAoProduto(Pedido pedido) {
        pedido.setFormaPagamento(cadastroFormaPagamento.buscar(pedido.getFormaPagamento().getId()));
        pedido.setRestaurante(cadastroRestaurante.buscar(pedido.getRestaurante().getId()));
        pedido.getEnderecoEntrega().setCidade(cadastroCidade.buscar(pedido.getEnderecoEntrega().getCidade().getId()));
        pedido.setCliente(cadastroUsuario.buscar(pedido.getCliente().getId()));

        if (!pedido.getRestaurante().aceitaFormaPagamento(pedido.getFormaPagamento()))
            throw new NegocioException(String.format("Forma de pagamento %s não é aceita por esse restaurante",
                    pedido.getFormaPagamento().getDescricao()));
    }

    private void adicionarProdutosNoItem(List<ItemPedido> itens, Long restauranteId) {
        itens.forEach(itemPedido -> {
            itemPedido.setProduto(cadastroProduto.buscar(restauranteId, itemPedido.getProduto().getId()));
            itemPedido.setPrecoUnitario(itemPedido.getProduto().getPreco());

        });
    }
}
