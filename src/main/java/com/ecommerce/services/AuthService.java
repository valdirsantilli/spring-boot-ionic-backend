package com.ecommerce.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.Cliente;
import com.ecommerce.repositories.ClienteRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepo.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado");
		}

		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepo.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) { // gera um digito
			return (char) (random.nextInt(10) + 48);
		}else if(opt == 1) { // gera uma letra maiuscula
			return (char) (random.nextInt(10) + 65);
		}else { // gera uma letra minuscula
			return (char) (random.nextInt(10) + 97);
		}
	}
}
