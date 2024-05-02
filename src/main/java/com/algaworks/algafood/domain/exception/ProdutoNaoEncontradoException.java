package com.algaworks.algafood.domain.exception;

import org.springframework.stereotype.Component;


public class ProdutoNaoEncontradoException  extends  NegocioException{
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe produto cadastrado com o código %d para o restaurante código %d", produtoId, restauranteId));
    }
}
