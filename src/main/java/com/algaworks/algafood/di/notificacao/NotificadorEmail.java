package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Profile("prod")
@TipoDoNotificador(NivelUrgencia.NORMAL)
//@Qualifier("normal")
@Component
public class NotificadorEmail implements Notificador {
	
//	private boolean caixaAlta;
//	private String hostServidorSmtp;
	
	public NotificadorEmail() {				
		System.out.println("NotificadorEmail REAL");
	}
	
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {		
		System.out.printf("Notificando %s através do e-mail %s: %s\n",
				cliente.getNome(), cliente.getEmail(),  mensagem);
	}

}
