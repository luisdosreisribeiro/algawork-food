package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/pedidos/{codigoPedido}")
public class PedidoFluxoController {
    private final FluxoPedidoService fluxoPedidoService;

    public PedidoFluxoController(
            FluxoPedidoService fluxoPedidoService){
        this.fluxoPedidoService = fluxoPedidoService;
    }
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar( @PathVariable String codigoPedido){
        fluxoPedidoService.confirmar(codigoPedido);
    }
    @PutMapping("/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(String codigoPedido){
        fluxoPedidoService.cancelar(codigoPedido);
    }

    @PutMapping("/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(String codigoPedido){
        fluxoPedidoService.entregar(codigoPedido);
    }

}
