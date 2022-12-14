package com.algaworks.algafood.api.model;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeModel {
	
	private Long id;
	private String nome;
	private EstadoModel estado;

}
