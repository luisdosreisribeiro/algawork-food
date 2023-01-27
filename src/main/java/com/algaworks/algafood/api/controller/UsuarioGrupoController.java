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

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
	
	private final GrupoModelAssembler grupoModelAssembler;
	private final CadastroUsuarioService cadastroUsuarioService;
	private final CadastroGrupoService cadastroGrupoService;
	
	public UsuarioGrupoController(
			GrupoModelAssembler grupoModelAssembler, 
			CadastroUsuarioService cadastroUsuarioService,
			CadastroGrupoService cadastroGrupoService) {
		
		this.grupoModelAssembler = grupoModelAssembler;
		this.cadastroUsuarioService = cadastroUsuarioService;
		this.cadastroGrupoService = cadastroGrupoService;
	}
	
	@GetMapping
	public List<GrupoModel> listar(@PathVariable Long usuarioId){
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);				
		
		return grupoModelAssembler.toCollectionModel(usuario.getGrupos());		
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associaGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.associaGrupo(usuarioId, grupoId);		
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociaGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.desassociaGrupo(usuarioId, grupoId);
	}
	

}
