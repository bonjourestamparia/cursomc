package com.bonjourestamparia.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bonjourestamparia.domain.Categoria;
import com.bonjourestamparia.domain.Produto;
import com.bonjourestamparia.dto.CategoriaDTO;
import com.bonjourestamparia.dto.ProdutoDTO;
import com.bonjourestamparia.resources.utils.URL;
import com.bonjourestamparia.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {		
		Produto ped = service.buscar(id);
		
		return ResponseEntity.ok(ped);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
				@RequestParam(value="nome", defaultValue = "") String nome,
				@RequestParam(value="categorias", defaultValue = "") String categorias,
				@RequestParam(value="page", defaultValue = "0") Integer numPage, 
				@RequestParam(value="linesPerPage", defaultValue = "10")Integer linesPerPages, 
				@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
				@RequestParam(value="direction", defaultValue = "ASC") String direction) {		
		
		List<Integer> ids = URL.decodeIntList(categorias);
		
		Page<Produto> list = service.search(URL.decodeParam(nome), ids, numPage, linesPerPages, orderBy, direction);
		Page<ProdutoDTO> listaDTO = list.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok(listaDTO);
	}	
	
}
