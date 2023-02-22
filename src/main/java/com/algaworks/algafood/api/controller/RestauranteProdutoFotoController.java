package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private CatalogoFotoProdutoService catalogoFotoProdutoService;
    private CadastroProdutoService cadastroProdutoService;
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;
    private FotoStorageService fotoStorage;

    public RestauranteProdutoFotoController(CatalogoFotoProdutoService catalogoFotoProdutoService,
                                            CadastroProdutoService cadastroProdutoService,
                                            FotoProdutoModelAssembler fotoProdutoModelAssembler,
                                            ProdutoRepository produtoRepository,
                                            FotoStorageService fotoStorage){
        this.catalogoFotoProdutoService = catalogoFotoProdutoService;
        this.cadastroProdutoService = cadastroProdutoService;
        this.fotoProdutoModelAssembler = fotoProdutoModelAssembler;
        this.fotoStorage = fotoStorage;
    }
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoSalva);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto (@PathVariable Long restauranteId,
                        @PathVariable Long produtoId,@RequestHeader(name = "accept") String acceptHeader)
                           throws HttpMediaTypeNotAcceptableException {
        try{
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);
            verificarCompatibilidadeType(mediaTypeFoto, mediaTypeAceitas);

            InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(inputStream));
        }catch(EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }

    }

    private void verificarCompatibilidadeType(MediaType mediaTypeFoto,
                                              List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if(!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }
    }
}
