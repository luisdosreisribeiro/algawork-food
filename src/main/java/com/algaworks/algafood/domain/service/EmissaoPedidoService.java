package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {
    private final PedidoRepository pedidoRepository;

    public EmissaoPedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido buscarOuFalhar(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(()->new PedidoNaoEncontradoException(pedidoId));
    }
}
