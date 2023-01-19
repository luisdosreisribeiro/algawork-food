package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser excluído por estar em uso. ";
	


	private final RestauranteRepository restauranteRepository;	
	private final CadastroCozinhaService cadastroCozinhaService;	
	private final CadastroCidadeService cadastroCidadeService;	
	private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
	private final CadastroUsuarioService cadastroUsuarioService;
	
	public CadastroRestauranteService(
			RestauranteRepository restauranteRepository,
			CadastroCozinhaService cadastroCozinhaService,
			CadastroCidadeService cadastroCidadeService,
			CadastroFormaPagamentoService cadastroFormaPagamentoService,
			CadastroUsuarioService cadastroUsuarioService
			) {
		this.restauranteRepository = restauranteRepository;
		this.cadastroCozinhaService = cadastroCozinhaService;
		this.cadastroCidadeService = cadastroCidadeService;
		this.cadastroFormaPagamentoService = cadastroFormaPagamentoService;
		this.cadastroUsuarioService = cadastroUsuarioService;		
	}
	

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();		
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);	
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
		}catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formasPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formasPagamentoId);		
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formasPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formasPagamentoId);		
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(()-> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);		
		restauranteAtual.ativar();
		
	}
	
	@Transactional
	public void ativar(List<Long> restaurantesId) {
		restaurantesId.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);		
		restauranteAtual.inativar();		
	}
	
	@Transactional
	public void inativar(List<Long> restaurantesId) {
		restaurantesId.forEach(this::inativar);
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long restauranteId){
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.fechar();
	}
	
	
	
	@Transactional
	public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void removerResponsavel(Long restauranteId, Long usuarioId) {
		
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
		
	}
	
}
