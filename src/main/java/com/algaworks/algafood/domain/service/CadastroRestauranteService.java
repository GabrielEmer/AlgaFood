package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        restaurante.setCozinha(cadastroCozinha.buscar(restaurante.getCozinha().getId()));
        restaurante.getEndereco().setCidade(cadastroCidade.buscar(restaurante.getEndereco().getCidade().getId()));
        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId).orElseThrow(
                () -> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
        Restaurante restaurante = buscar(restauranteId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
        Restaurante restaurante = buscar(restauranteId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.inativar();
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.fechar();
    }
}
