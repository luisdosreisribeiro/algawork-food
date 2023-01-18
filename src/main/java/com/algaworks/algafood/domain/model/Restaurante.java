package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;
@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;	
	
		
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;	
	
	
	@ManyToOne 
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	private boolean ativo = true;
//	private Boolean ativo  = true;
	
	private Boolean aberto = true;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
	joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet();
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	//Parou de reconher gettes e setter do lombok
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}

	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}

	public Cozinha getCozinha() {
		return cozinha;
	}

	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getAberto() {
		return aberto;
	}

	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}

	public OffsetDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(OffsetDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public OffsetDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(OffsetDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Set<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(Set<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Set<Usuario> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(Set<Usuario> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public Boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public Boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	public void abrir() {
		setAberto(true);
	}
	
	
	public void fechar() {
		setAberto(false);
	}
	
	public Boolean adicionarResponsavel(Usuario usuario) {
		return getResponsaveis().add(usuario);
	}
	
	public Boolean removerResponsavel(Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}

}
