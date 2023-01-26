package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	
	private Integer quantidade;
	
	private BigDecimal precoUnitario;
	
	private BigDecimal precoTotal;
	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	public void calcularPrecoTotal(){
		BigDecimal precoUntario = this.getPrecoUnitario();
		Integer quantidade = this.getQuantidade();

		if(precoUntario == null){
			precoUntario= BigDecimal.ZERO;
		}
		if(quantidade == null){
			quantidade = 0;
		}
		this.setPrecoTotal(precoUntario.multiply(new BigDecimal(quantidade)));
	}
	

}
