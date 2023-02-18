package com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	private final ProdutoRepository produtoRepository;
	
	public CadastroProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
		
	}
	
	
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto buscarOuFalhar( Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId,produtoId)
				.orElseThrow(()-> new ProdutoNaoEncontradoException(restauranteId, produtoId));
		
	}

}
