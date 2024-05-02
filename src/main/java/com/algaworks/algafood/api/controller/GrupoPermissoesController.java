package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissoesController {

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

//    @GetMapping
//    public List<PermissaoModel> listarPermissoes(@PathVariable Long grupoId) {
//        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
//
//        List<Permissao> permissoes = grupo.getPermissoes();
//
//        return permissaoModelAssembler.toCollectionModel(permissoes);
//    }

    @GetMapping
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }


    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociaPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {

        cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associaPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {

        cadastroGrupoService.associarPermissao(grupoId, permissaoId);
    }

}
