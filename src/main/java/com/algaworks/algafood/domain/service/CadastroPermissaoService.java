package com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
	
	private final PermissaoRepository permissaoRepository;
	
	public CadastroPermissaoService(PermissaoRepository permissaoRepository) {
		this.permissaoRepository = permissaoRepository;
	}
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(()-> new PermissaoNaoEncontradaException(permissaoId));
	}

}
