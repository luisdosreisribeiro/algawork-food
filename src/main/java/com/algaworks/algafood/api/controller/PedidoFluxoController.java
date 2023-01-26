package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/pedidos/{pedidoId}")
public class PedidoFluxoController {
    private final FluxoPedidoService fluxoPedidoService;

    public PedidoFluxoController(
            FluxoPedidoService fluxoPedidoService){
        this.fluxoPedidoService = fluxoPedidoService;
    }
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoId){
        fluxoPedidoService.confirmar(pedidoId);
    }
    @PutMapping("/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long pedidoId){
        fluxoPedidoService.cancelar(pedidoId);
    }

    @PutMapping("/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable Long pedidoId){
        fluxoPedidoService.entregar(pedidoId);
    }

}
