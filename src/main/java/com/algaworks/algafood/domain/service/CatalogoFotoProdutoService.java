package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    private ProdutoRepository produtoRepository;
    private FotoStorageService fotoStorage;

    public CatalogoFotoProdutoService(ProdutoRepository produtoRepository,
                                      FotoStorageService fotoStorage){
        this.produtoRepository = produtoRepository;
        this.fotoStorage = fotoStorage;
    }
    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo){
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        if(fotoExistente.isPresent()){
            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto =  produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                        .nomeArquivo(foto.getNomeArquivo())
                                .inputStream(dadosArquivo)
                                        .build();

        fotoStorage.armazenar(novaFoto);

        return foto;

    }
}
