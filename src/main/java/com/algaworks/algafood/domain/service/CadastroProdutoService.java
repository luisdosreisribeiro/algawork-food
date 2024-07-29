package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto buscarOuFalhar (Long restauranteid, Long produtoId) {
        return produtoRepository.findById( restauranteid,produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteid, produtoId));
        }

    public Produto adicionar(Produto produto) {
        return produtoRepository.save(produto);
    }


}
