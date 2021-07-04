package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(
                () -> new EntidadeNaoEncontradaException(getMsgCidadeNaoCadastrada(cidadeId)));
    }

    private String getMsgCidadeNaoCadastrada(Long cidadeId) {
        return String.format("Não existe um cadastro de cidade com código %d", cidadeId);
    }

    public Cidade salvar(Cidade cidade) {
        cidade.setEstado(cadastroEstado.buscar(cidade.getEstado().getId()));
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    getMsgCidadeNaoCadastrada(cidadeId));
    } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoException(
                String.format("Cidade de código %d não pode ser removida pois está em uso", cidadeId)
        );
        }
    }
}
