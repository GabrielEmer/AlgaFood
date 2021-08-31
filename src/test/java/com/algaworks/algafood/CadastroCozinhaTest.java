package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class CadastroCozinhaTest {

    @Autowired
    CadastroCozinhaService cozinhaService;

	@Test
	public void deveAtribuirIdQuandoCadastrarCozinhaComDadosCorretos() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		Cozinha novaCozinha = cozinhaService.salvar(cozinha);

		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void deveFalharQuandoCadastrarCozinhaSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

        Assertions.assertThrows(ConstraintViolationException.class, () -> cozinhaService.salvar(cozinha));
	}

	@Test
	public void deveFalharQuandoExcluirCozinhaEmUso() {
        Assertions.assertThrows(EntidadeEmUsoException.class, () -> cozinhaService.excluir(1L));
	}

	@Test
	public void deveFalharQuandoExcluirCozinhaInexistente() {
        Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> cozinhaService.excluir(9128L));
	}
}
