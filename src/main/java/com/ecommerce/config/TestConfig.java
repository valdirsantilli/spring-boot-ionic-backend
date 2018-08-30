package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ecommerce.services.DBService;
import com.ecommerce.services.EmailService;
import com.ecommerce.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	public DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
