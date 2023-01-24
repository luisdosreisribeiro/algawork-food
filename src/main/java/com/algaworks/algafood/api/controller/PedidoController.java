package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoController {
    private final PedidoRepository pedidoRepository;
    private PedidoModelAssembler pedidoModelAssembler;
    private EmissaoPedidoService emissaoPedidoService;
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;


    public PedidoController(
            PedidoRepository pedidoRepository,
            PedidoModelAssembler pedidoModelAssembler,
            EmissaoPedidoService emissaoPedidoService,
            PedidoResumoModelAssembler pedidoResumoModelAssembler){
        this.pedidoRepository = pedidoRepository;
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.emissaoPedidoService = emissaoPedidoService;
        this.pedidoResumoModelAssembler = pedidoResumoModelAssembler;
    }
    @GetMapping
    public List<PedidoResumoModel>listar(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoResumoModelAssembler.toCollection(pedidos);
    }
    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        return pedidoModelAssembler.toModel(pedido);
    }
}
