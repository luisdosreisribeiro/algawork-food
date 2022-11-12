package com.algaworks.algafood.di.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@TipoDoNotificador(NivelUrgencia.NORMAL)
	@Autowired
	private Notificador notificador;	
	
	
//
//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;		
//	}	


	public void ativar(Cliente cliente) {
		cliente.ativar();			
		
		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
				
		
	}
	
	
	
}
