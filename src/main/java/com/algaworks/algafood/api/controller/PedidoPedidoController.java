package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/pedidos/{pedidoId}")
public class PedidoPedidoController {
    private final FluxoPedidoService fluxoPedidoService;

    public PedidoPedidoController(
            FluxoPedidoService fluxoPedidoService){
        this.fluxoPedidoService = fluxoPedidoService;
    }
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoId){
        fluxoPedidoService.confirmar(pedidoId);
    }

}
