package com.bonjourestamparia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.Categoria;
import com.bonjourestamparia.domain.Produto;
import com.bonjourestamparia.repositories.CategoriaRepository;
import com.bonjourestamparia.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> cat = repo.findById(id);
		return cat.orElse(null);
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer numPage, Integer linesPerPages, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(numPage, linesPerPages, Direction.valueOf(direction) , orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return repo.search(nome, categorias, pageRequest);
		
	}
	
}
