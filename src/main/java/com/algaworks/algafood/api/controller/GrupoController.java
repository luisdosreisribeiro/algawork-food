package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	GrupoModelAssembler grupoModelAssembler;	
	
	@Autowired
	CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	GrupoInputDisassembler grupoInputDisassembler;
	

	@GetMapping
	public List<GrupoModel> listar() {
		List<Grupo> grupos = grupoRepository.findAll();
		return grupoModelAssembler.toCollectionModel(grupos);		
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {

		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		return grupoModelAssembler.toModel(grupo);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody GrupoInput grupoInput) {
		
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);		
		
		return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));		
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoAtual));
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId ){
		cadastroGrupoService.excluir(grupoId);
	}
	
	
}
