package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {
    private final ModelMapper modelMapper;

    public PedidoInputDisassembler(
            ModelMapper modelMapper
    ){
        this.modelMapper = modelMapper;
    }
    public Pedido toDomainObject(PedidoInput pedidoInput){
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido){
        modelMapper.map(pedidoInput, pedido);
    }
}
