package com.bonjourestamparia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.Pedido;
import com.bonjourestamparia.repositories.PedidoRepository;
import com.bonjourestamparia.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> ped = repo.findById(id);
		return ped.orElseThrow(()-> new ObjectNotFoundException("Pedido não encontrado"));
	}
}
