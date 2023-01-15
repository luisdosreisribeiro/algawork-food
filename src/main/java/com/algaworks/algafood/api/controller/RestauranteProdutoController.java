package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
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
	
	public RestauranteProdutoController(
			CadastroRestauranteService cadastroServiceRestaurante,
			ProdutoModelAssembler produtoModelAssembler,
			CadastroProdutoService cadastroProdutoSertive,
			ProdutoRepository produtoRepository) 
	{
		this.cadastroServiceRestaurante = cadastroServiceRestaurante;
		this.produtoModelAssembler = produtoModelAssembler;
		this.cadastroProdutoSertive = cadastroProdutoSertive;
		this.produtoRepository = produtoRepository;
		
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

}
