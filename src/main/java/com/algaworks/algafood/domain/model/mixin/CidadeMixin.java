package com.algaworks.algafood.domain.model.mixin;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {	
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

}
