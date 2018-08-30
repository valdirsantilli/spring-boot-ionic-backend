package com.ecommerce.services;

import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}