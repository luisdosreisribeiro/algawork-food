package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
//	List<FormaPagamento> listar();
//	FormaPagamento buscar(Long id);
//	FormaPagamento salvar(FormaPagamento formaPagamento);
//	void remover(FormaPagamento formaPagamento);

}
