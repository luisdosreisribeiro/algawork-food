package com.algaworks.algafood.domain.exception;

public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public FotoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoNaoEncontradaException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe um cadastro de foto de produto com código %d, " +
				"cadastrado para o restaurante de código %d", produtoId, restauranteId ));
	}

}
