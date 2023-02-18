package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    private ProdutoRepository produtoRepository;

    public CatalogoFotoProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }
    @Transactional
    public FotoProduto salvar(FotoProduto foto){
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        if(fotoExistente.isPresent()){
            produtoRepository.delete(fotoExistente.get());
        }

        return produtoRepository.save(foto);
    }
}
