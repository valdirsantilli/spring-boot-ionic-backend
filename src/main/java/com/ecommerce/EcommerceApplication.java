package com.ecommerce;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ecommerce.domain.Categoria;
import com.ecommerce.repositories.CategoriaRepository;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		categoriaRepository.saveAll(Arrays.asList(new Categoria(null,"Informática"), new Categoria(null, "Escritório")));
	}
}
