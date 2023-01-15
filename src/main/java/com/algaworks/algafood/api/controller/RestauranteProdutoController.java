package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	private final CadastroRestauranteService cadastroServiceRestaurante;
	private final ProdutoModelAssembler produtoModelAssembler;
	private final CadastroProdutoService cadastroProdutoSertive;
	private final ProdutoRepository produtoRepository;
	private final ProdutoInputDisassembler produtoInputDisassembler;
	
	public RestauranteProdutoController(
			CadastroRestauranteService cadastroServiceRestaurante,
			ProdutoModelAssembler produtoModelAssembler,
			CadastroProdutoService cadastroProdutoSertive,
			ProdutoRepository produtoRepository,
			ProdutoInputDisassembler produtoInputDisassembler) 
	{
		this.cadastroServiceRestaurante = cadastroServiceRestaurante;
		this.produtoModelAssembler = produtoModelAssembler;
		this.cadastroProdutoSertive = cadastroProdutoSertive;
		this.produtoRepository = produtoRepository;
		this.produtoInputDisassembler = produtoInputDisassembler;
		
	}
	
	@GetMapping
	public List <ProdutoModel> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroServiceRestaurante.buscarOuFalhar(restauranteId);
		
		List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);
		
		return produtoModelAssembler.toCollectionModel(produtos);				
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long  produtoId){
		Produto produto = cadastroProdutoSertive.buscarOuFalhar(restauranteId, produtoId);		
		return produtoModelAssembler.toModel(produto);				
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroServiceRestaurante.buscarOuFalhar(restauranteId);		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);

		produto.setRestaurante(restaurante);

		cadastroProdutoSertive.salvar(produto);

		return produtoModelAssembler.toModel(produto);	
	}

}
