package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {
	
	private final ModelMapper modelMapper;
	
	public ProdutoInputDisassembler(
			ModelMapper modelMapper
			) {
		this.modelMapper = modelMapper;		
	}
	
	public Produto  toDomainObject(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	

}
