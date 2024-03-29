package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class FormaPagamentoController {
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	FormaPagamentoModelAssembler formaPagamentoAssembler;
	
	@Autowired
	FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	
	@GetMapping
	public List<FormaPagamentoModel> listar() {
		return formaPagamentoAssembler.toCollectionModel( formaPagamentoRepository.findAll());		
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId) {
		
		return formaPagamentoAssembler.toModel(cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId));		
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento  formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);			

		return formaPagamentoAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));

	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel atualizar( @PathVariable Long formaPagamentoId,@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		return formaPagamentoAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));	
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamentoService.excluir(formaPagamentoId);
	}
	

}
