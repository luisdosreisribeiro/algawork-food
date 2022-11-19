package com.algaworks.algafood.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar(){
		
		List<Estado> estados = new ArrayList<>();		
		estados = estadoRepository.listar();			
		return ResponseEntity.ok(estados);
	}
	
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId){
		Estado estado = estadoRepository.buscar(estadoId);
		if (estado != null) {
			return ResponseEntity.ok(estado);
		}
		return ResponseEntity.notFound().build();			
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado){
		  return cadastroEstadoService.salvar(estado);				  
	}
	
	
	
	

}
