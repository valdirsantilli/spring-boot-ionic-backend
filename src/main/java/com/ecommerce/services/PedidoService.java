package com.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.Pedido;
import com.ecommerce.repositories.PedidoRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repository.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: "+ Pedido.class.getName()));
	}
}