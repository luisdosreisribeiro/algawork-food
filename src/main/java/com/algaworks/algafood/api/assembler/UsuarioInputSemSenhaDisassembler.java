package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioInputSemSenha;
import com.algaworks.algafood.domain.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UsuarioInputSemSenhaDisassembler {
	
	@Autowired
	ModelMapper modelMapper;
	
	public void copyToDomainObject (UsuarioInputSemSenha usuarioInputSemSenha, Usuario usuario) {
		modelMapper.map(usuarioInputSemSenha, usuario);
		
	}

}
