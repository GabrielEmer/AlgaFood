package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    public Usuario buscar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioCadastrado = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario))
            throw new NegocioException(String.format("Já existe um cadastro com o e-mail %s", usuario.getEmail()));

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioId);
        } catch (
                DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Usuário com código %d não pode ser removido, pois está em uso", usuarioId));
        }
    }

    @Transactional
    public void atualizarSenha(Long usuarioId, String senhaAtual, String senhaNova) {
        Usuario usuario = buscar(usuarioId);

        if (!usuario.verificarSenhaValida(senhaAtual))
            throw new NegocioException("Senha informada não coincide com a senha registrada para esse usuário.");

        usuario.setSenha(senhaNova);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = cadastroGrupo.buscar(grupoId);
        usuario.removerGrupo(grupo);
    }
}