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

        if(!pedido.getStatus().equals(StatusPedido.CRIADO)){
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s", pedido.getId(),
                    pedido.getStatus().getDescricao(),StatusPedido.CONFIRMADO.getDescricao()));
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    public void cancelar(Long pedidoId){
        Pedido pedido =  emissaoPedidoService.buscarOuFalhar(pedidoId);
        if(!pedido.getStatus().equals(StatusPedido.CRIADO)){
           throw new NegocioException(
                   String.format("Status do pedido %d não pode ser alterado de %s para %s", pedido.getId(),
                           pedido.getStatus().getDescricao(),StatusPedido.CANCELADO.getDescricao()));
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    public void entregar (Long pedidoId){
        Pedido pedido =  emissaoPedidoService.buscarOuFalhar(pedidoId);
        if(!pedido.getStatus().equals(StatusPedido.CONFIRMADO)){
            throw new NegocioException(
                    String.format("Status do %d pedido não pode ser alterado de %s para %s ", pedido.getId(),
                            pedido.getStatus().getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
        }
        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }
}
