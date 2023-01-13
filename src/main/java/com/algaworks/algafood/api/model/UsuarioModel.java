package com.algaworks.algafood.api.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
	
	private Long id;		
	private String nome;		
	private String email;	

}
