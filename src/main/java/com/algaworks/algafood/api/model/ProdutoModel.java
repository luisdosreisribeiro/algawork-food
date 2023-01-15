package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {
	
	private Long id;	
	private String nome;	
	private String descricao;	
	private BigDecimal preco;	
	private Boolean ativo;

}
