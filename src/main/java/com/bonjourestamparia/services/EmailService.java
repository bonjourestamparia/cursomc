package com.bonjourestamparia.services;

import org.springframework.mail.SimpleMailMessage;

import com.bonjourestamparia.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
