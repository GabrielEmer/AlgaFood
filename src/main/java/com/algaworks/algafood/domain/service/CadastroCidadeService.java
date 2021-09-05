package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(
                () -> new CidadeNaoEncontradaException(cidadeId));
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        cidade.setEstado(cadastroEstado.buscar(cidade.getEstado().getId()));
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new CidadeNaoEncontradaException(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida pois está em uso", cidadeId));
        }
    }
}
