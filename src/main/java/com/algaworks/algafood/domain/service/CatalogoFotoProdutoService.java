package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoFotoProdutoService {

    private ProdutoRepository produtoRepository;

    public CatalogoFotoProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }
    @Transactional
    public FotoProduto salvar(FotoProduto foto){
        return produtoRepository.save(foto);

    }
}
