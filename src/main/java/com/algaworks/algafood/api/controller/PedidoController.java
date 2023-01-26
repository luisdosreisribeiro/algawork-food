package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoController {
    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final EmissaoPedidoService emissaoPedidoService;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;


    public PedidoController(
            PedidoRepository pedidoRepository,
            PedidoModelAssembler pedidoModelAssembler,
            EmissaoPedidoService emissaoPedidoService,
            PedidoResumoModelAssembler pedidoResumoModelAssembler,
            PedidoInputDisassembler pedidoInputDisassembler){
        this.pedidoRepository = pedidoRepository;
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.emissaoPedidoService = emissaoPedidoService;
        this.pedidoResumoModelAssembler = pedidoResumoModelAssembler;
        this.pedidoInputDisassembler = pedidoInputDisassembler;
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput){
        try{
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            //TODO pegar usuario autenticado.
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            emissaoPedidoService.emitir(novoPedido);
            return pedidoModelAssembler.toModel(novoPedido);
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
