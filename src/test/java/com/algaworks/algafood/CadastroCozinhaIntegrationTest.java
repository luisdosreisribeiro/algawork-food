package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
class CadastroCozinhaIntegrationTest {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
				
		//ação		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);		
		
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();		
	}
	
	@Test
	public void testarCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		ConstraintViolationException erroEsperado = 
				Assertions.assertThrows(ConstraintViolationException.class, ()->{
					cadastroCozinha.salvar(novaCozinha);	
				});
		assertThat(erroEsperado).isNotNull();			
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {		
		
		EntidadeEmUsoException exception =
		assertThrows(EntidadeEmUsoException.class, ()->{
			cadastroCozinha.excluir(1L);
		});
		
		assertThat(exception).isNotNull();		
		
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		
		EntidadeNaoEncontradaException exception = 
		assertThrows(EntidadeNaoEncontradaException.class, ()->{
			cadastroCozinha.excluir(500L);
		});
		assertThat(exception).isNotNull();				
	}
	
}
