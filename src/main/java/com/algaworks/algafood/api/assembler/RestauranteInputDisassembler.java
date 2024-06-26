package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	

	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		//identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		if(restaurante.getCozinha() != null) {
			restaurante.setCozinha(new Cozinha());
		}
		
		//identifier of an instance of com.algaworks.algafood.domain.model.Cidade was altered from 1 to 2
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
		
	}

}
