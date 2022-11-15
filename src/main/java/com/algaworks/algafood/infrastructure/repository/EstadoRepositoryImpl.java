package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {
	
	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Estado> listar() {
		return manager.createQuery("from Estado", Estado.class).getResultList();		
	}

	@Override
	public Estado buscar(Long id) {
		return manager.find(Estado.class, id);		
	}

	@Override
	public Estado salvar(Estado estado) {
		return manager.merge(estado);		
	}

	@Override
	public void remover(Estado estado) {
		manager.remove(estado);		
	}	

}
