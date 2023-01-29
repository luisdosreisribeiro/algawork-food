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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;

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
//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//        List<Pedido> pedidos = pedidoRepository.findAll();
//        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollection(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)){
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//        pedidosWrapper.setFilters(filterProvider);
//        return pedidosWrapper;
//    }

    @GetMapping
    public List<PedidoResumoModel>listar(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoResumoModelAssembler.toCollection(pedidos);
    }
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
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
