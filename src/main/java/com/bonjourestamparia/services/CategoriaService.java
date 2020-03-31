package com.bonjourestamparia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.Categoria;
import com.bonjourestamparia.repositories.CategoriaRepository;
import com.bonjourestamparia.services.exceptions.DataIntegrityException;
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
	
	public Categoria update(Categoria obj) {
		buscar(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}	
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
}
