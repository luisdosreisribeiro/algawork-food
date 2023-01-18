package com.algaworks.algafood.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	private static final String MSG_GRUPO_EM_USO = "O grupo de código %d não pode ser excluído, pois está em uso.";
	
	private final GrupoRepository grupoRepository;
	private final CadastroPermissaoService cadastroPermissaoService;
	
	public CadastroGrupoService(
			GrupoRepository grupoRepository,
			CadastroPermissaoService cadastroPermissaoService
			) {
		this.grupoRepository = grupoRepository;
		this.cadastroPermissaoService = cadastroPermissaoService;
		
	}
	
	public Grupo buscarOuFalhar(Long grupoId) {
		
		Grupo grupo = grupoRepository.findById(grupoId)
				.orElseThrow(()-> new GrupoNaoEncontradoException (grupoId));
		return grupo;
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {		
		return grupoRepository.save(grupo);				
	}
	
	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		}catch(DataIntegrityViolationException e ) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	@Transactional
	public void associarPermissao(Long permissaoId, Long grupoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		
		grupo.associaPermissao(permissao);
	}
	
	@Transactional
	public void desassociarPermissao(Long permissaoId, Long grupoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		
		grupo.desassociaPermissao(permissao);
	}
}

