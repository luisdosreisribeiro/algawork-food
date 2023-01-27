package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String email;

	
	@Column(nullable = false)	
	private String senha;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition =  "datetime")
	private OffsetDateTime dataCadastro;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo", 
	joinColumns = @JoinColumn(name = "usuario_id"), 
	inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private Set<Grupo> grupos = new HashSet<>();
	
	
	//Parou de funcionar getters e setters do lombok
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public OffsetDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(OffsetDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Boolean senhaCoincideCom(String senha) {
		return getSenha().equals(senha);
	}

	public Boolean senhaNaoCoincidemCom(String senha) {
		return !senhaCoincideCom(senha);
	}
	
	public Boolean associaGrupo(Grupo grupo) {
		return getGrupos().add(grupo);
	}
	
	public Boolean desassociaGrupo(Grupo grupo) {
		return getGrupos().remove(grupo);
	}
}
