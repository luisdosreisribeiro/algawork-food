package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Profile("dev")
@TipoDoNotificador(NivelUrgencia.NORMAL)
//@Qualifier("normal")
@Component
public class NotificadorEmailMock implements Notificador {
	
//	private boolean caixaAlta;
//	private String hostServidorSmtp;
	
	public NotificadorEmailMock() {	
		System.out.println("Notificador email MOCK");
		
	}
	
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {		
		System.out.printf("MOCK : Notificação seria enviada para  %s através do e-mail %s: %s\n",
				cliente.getNome(), cliente.getEmail(),  mensagem);
	}

}
