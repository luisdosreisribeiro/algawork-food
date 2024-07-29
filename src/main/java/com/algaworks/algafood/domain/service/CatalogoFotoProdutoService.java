package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoExistente.isPresent()){
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }
        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .contentType(foto.getContentType())
                .inputStream(dadosArquivo)
                .build();
        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return  foto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {

        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);

        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorage.remover(fotoProduto.getNomeArquivo());
    }
}
