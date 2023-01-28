package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.view.RestauranteView;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	

	private final RestauranteRepository restauranteRepository;
	private final CadastroRestauranteService cadastroRestauranteService;
	private final RestauranteModelAssembler restauranteModelAssembler;
	private final RestauranteInputDisassembler restauranteInputDisassembler;


	public RestauranteController(
			RestauranteRepository restauranteRepository,
			CadastroRestauranteService cadastroRestauranteService,
			RestauranteModelAssembler restauranteModelAssembler,
			RestauranteInputDisassembler restauranteInputDisassembler

	){
		this.restauranteRepository = restauranteRepository;
		this.cadastroRestauranteService = cadastroRestauranteService;
		this.restauranteModelAssembler = restauranteModelAssembler;
		this.restauranteInputDisassembler = restauranteInputDisassembler;
	}

	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteModel> listar(){
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteModel> listarApenasNomes(){
		return listar();
	}

//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false)String projecao){
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restauranteModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restauranteModel);
//
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//		if("apenas-nome".equals(projecao)){
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		}else if("completo".equals(projecao)){
//			restaurantesWrapper.setSerializationView(null);
//		}
//		return restaurantesWrapper;
//	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante =  cadastroRestauranteService.buscarOuFalhar(restauranteId);		
		
		return restauranteModelAssembler.toModel(restaurante);
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public RestauranteModel adicionar( @RequestBody @Valid RestauranteInput restauranteInput){	
		try {
			Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
		}catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw  new NegocioException (e.getMessage(), e);
		}		
	}
	
	
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId, 
			@RequestBody @Valid RestauranteInput restauranteInput){
		try {
			//Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
//			
//			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");			


			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));	

		}catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}			
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){		
			cadastroRestauranteService.excluir(restauranteId);

	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar( @PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);		
	}
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		cadastroRestauranteService.ativar(restaurantesIds);		
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestauranteService.inativar(restaurantesIds);	
		}catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}			
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		try {
			cadastroRestauranteService.inativar(restauranteId);
		}catch(RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}		
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
		
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
		
	}
	
}
