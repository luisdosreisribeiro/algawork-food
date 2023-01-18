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

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {
	
	private final CadastroUsuarioService cadastroUsuarioService;
	private final UsuarioModelAssembler usuarioModelAssembler;
	private final CadastroRestauranteService cadastroRestauranteService;
	
	public RestauranteUsuarioResponsavelController(
			CadastroUsuarioService cadastroUsuarioService, 
			UsuarioModelAssembler usuarioModelAssembler,
			CadastroRestauranteService cadastroRestauranteService) {
		this.cadastroUsuarioService = cadastroUsuarioService;
		this.usuarioModelAssembler = usuarioModelAssembler;
		this.cadastroRestauranteService = cadastroRestauranteService;
	}
	
	@GetMapping
	public List<UsuarioModel> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis());			
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarResponsavel(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
		cadastroRestauranteService.adicionarResponsavel(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deassociarResponsavel(@PathVariable Long usuarioId, @PathVariable Long restauranteId) {
		cadastroRestauranteService.removerResponsavel(restauranteId, usuarioId);
	}

}
