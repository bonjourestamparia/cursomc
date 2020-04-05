package com.bonjourestamparia.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bonjourestamparia.services.DBService;
import com.bonjourestamparia.services.EmailService;
import com.bonjourestamparia.services.MockMailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockMailService();
	}
}
