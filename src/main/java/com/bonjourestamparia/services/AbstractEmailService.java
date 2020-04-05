package com.bonjourestamparia.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.bonjourestamparia.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private  String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage msg = prepareSimpleMailMessageFromPedido(obj);
		
		sendEmail(msg);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setTo(obj.getCliente().getEmail());
		msg.setFrom(sender);
		msg.setSubject("Pedido confirmado");
		msg.setSentDate(Calendar.getInstance().getTime());
		msg.setText(obj.toString());
		
		return msg;
	}
}
