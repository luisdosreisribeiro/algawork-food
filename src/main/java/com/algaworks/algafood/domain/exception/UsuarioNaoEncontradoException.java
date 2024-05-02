package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends  EntidadeNaoEncontradaException{
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException (Long usuarioId) {
        this(String.format("Não existe usuário cadastrado com o código %d", usuarioId));
    }
}
