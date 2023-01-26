package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;

    public FluxoPedidoService(
            EmissaoPedidoService emissaoPedidoService
    ){
        this.emissaoPedidoService = emissaoPedidoService;
    }

    @Transactional
    public void confirmar(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.confirmar();
    }
    @Transactional
    public void cancelar(Long pedidoId){
        Pedido pedido =  emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.cancelar();
    }
    @Transactional
    public void entregar (Long pedidoId){
        Pedido pedido =  emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.entregar();
    }
}
