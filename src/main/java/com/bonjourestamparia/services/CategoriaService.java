package com.bonjourestamparia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.Categoria;
import com.bonjourestamparia.repositories.CategoriaRepository;
import com.bonjourestamparia.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> cat = repo.findById(id);
		return cat.orElseThrow(()-> new ObjectNotFoundException("Categoria não encontrada"));
	}
	
	public Categoria insert(Categoria obj) {
		return repo.save(obj);
	}
}
