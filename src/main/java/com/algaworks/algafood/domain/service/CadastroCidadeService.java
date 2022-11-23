package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();		
		Estado estado = estadoRepository.buscar(cidade.getEstado().getId());

		if(estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe estado cadastrado com o código %d", estadoId ));
		}
		cidade.setEstado(estado);

		return cidadeRepository.salvar(cidade);
	}

	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.remover(cidadeId);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade com o código %d não pode ser removida, pois está em uso", cidadeId));
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cidade com o código %d", cidadeId));		
		}

	}
}	
