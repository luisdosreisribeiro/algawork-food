package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissoesController {
	
	private final PermissaoModelAssembler permissaoModelAssembler;
	private final CadastroGrupoService cadastroGrupoService;
	private final CadastroPermissaoService cadastroPermissaoService;
	
	public GrupoPermissoesController(
			PermissaoModelAssembler permissaoModelAssembler,
			CadastroGrupoService cadastroGrupoService,
			CadastroPermissaoService cadastroPermissaoService) {
		this.permissaoModelAssembler = permissaoModelAssembler;
		this.cadastroGrupoService = cadastroGrupoService;
		this.cadastroPermissaoService = cadastroPermissaoService;
	}
	
	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long grupoId){
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

		return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());		
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarPermissao(@PathVariable Long permissaoId, @PathVariable Long grupoId) {
		cadastroGrupoService.desassociarPermissao(permissaoId, grupoId);
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarPermissao(@PathVariable Long permissaoId, @PathVariable Long grupoId) {
		cadastroGrupoService.associarPermissao(permissaoId, grupoId);
	}

}
