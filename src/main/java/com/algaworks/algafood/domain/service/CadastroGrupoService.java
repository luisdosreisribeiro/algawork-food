package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	private static final String MSG_GRUPO_EM_USO = "O grupo de código %d não pode ser excluído, pois está em uso.";
	@Autowired
	GrupoRepository grupoRepository;
	
	public Grupo buscarOuFalhar(Long grupoId) {
		
		Grupo grupo = grupoRepository.findById(grupoId)
				.orElseThrow(()-> new GrupoNaoEncontradoException (grupoId));
		return grupo;
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {		
		return grupoRepository.save(grupo);				
	}
	
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
		}catch(EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		}catch(DataIntegrityViolationException e ) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

}

