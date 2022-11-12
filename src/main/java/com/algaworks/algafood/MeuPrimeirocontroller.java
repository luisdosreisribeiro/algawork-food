package com.algaworks.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
public class MeuPrimeirocontroller {
	
	private AtivacaoClienteService ativacaoClienteService;	
	
		
	public MeuPrimeirocontroller(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;
	
	}


	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		Cliente joao = new Cliente("joão", "joao@xyz.com.br", "33564546");
		ativacaoClienteService.ativar(joao);		
		
		return "Hello!";
		
	}

}
