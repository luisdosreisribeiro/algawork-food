package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser excluído por estar em uso. ";

	private static final String MSG_RESTAURANTE_INEXISTENTE = "Não existe restaurante com o código %d. ";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		//		Opção 1
		//		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		//		
		//		if(cozinha.isEmpty()) {
		//			throw new EntidadeNaoEncontradaException(
		//					String.format("Não existe cadastro de cozinha com código %d ", cozinhaId));
		//		}	
		//		restaurante.setCozinha(cozinha.get());
		//		
		//  	Opção 2
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(()->new EntidadeNaoEncontradaException(
						String.format("Não existe cadastro de cozinha com código %d ", cozinhaId)));
		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException (String.format(MSG_RESTAURANTE_INEXISTENTE, restauranteId));
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(()-> new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_INEXISTENTE, restauranteId)));
	}
}
