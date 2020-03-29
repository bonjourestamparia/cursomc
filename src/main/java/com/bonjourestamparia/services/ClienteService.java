package com.bonjourestamparia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.repositories.ClienteRepository;
import com.bonjourestamparia.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cli = repo.findById(id);
		return cli.orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrado"));
	}
}
