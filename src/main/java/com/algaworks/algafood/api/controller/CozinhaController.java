package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value ="/cozinhas")
public class CozinhaController {
	private final CadastroCozinhaService cadastroCozinhaService;
	private final CozinhaRepository cozinhaRepository;
	@Autowired
	private final CozinhaModelAssembler cozinhaModelAssembler;
	private final CozinhaInputDisassembler cozinhaInputDisassembler;

	public CozinhaController(
			CadastroCozinhaService cadastroCozinhaService,
			CozinhaRepository cozinhaRepository,
			CozinhaModelAssembler cozinhaModelAssembler,
			CozinhaInputDisassembler cozinhaInputDisassembler
	){
		this.cadastroCozinhaService = cadastroCozinhaService;
		this.cozinhaRepository = cozinhaRepository;
		this.cozinhaModelAssembler = cozinhaModelAssembler;
		this.cozinhaInputDisassembler = cozinhaInputDisassembler;
	}
	@GetMapping
	public Page<CozinhaModel>listar(@PageableDefault(size=2) Pageable pageable){
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());

		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable,
				cozinhasPage.getTotalElements());

		return cozinhasModelPage;
	}

	
	@GetMapping("/{id}")
	public CozinhaModel buscar(@PathVariable Long id) {		
		return cozinhaModelAssembler.toModel(cadastroCozinhaService.buscarOuFalhar(id));
		
//		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
//		
//		if(cozinha.isPresent()) {
//			return ResponseEntity.ok(cozinha.get());
//		}		
//		
//		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);			
		
		return cozinhaModelAssembler.toModel(cadastroCozinhaService.salvar(cozinha));
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,@RequestBody @Valid CozinhaInput cozinhaInput){

		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);	
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		return cozinhaModelAssembler.toModel(cadastroCozinhaService.salvar(cozinhaAtual));

	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){	
		
			cadastroCozinhaService.excluir(cozinhaId);		
							
	}

}
