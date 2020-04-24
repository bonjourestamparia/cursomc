package com.bonjourestamparia.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bonjourestamparia.domain.Categoria;
import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.dto.CategoriaDTO;
import com.bonjourestamparia.dto.ClienteDTO;
import com.bonjourestamparia.dto.ClienteNewDTO;
import com.bonjourestamparia.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {		
		Cliente cli = service.buscar(id);
		
		return ResponseEntity.ok(cli);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){
		Cliente obj = service.insert(service.fromDTO(objDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		objDTO.setId(id);
		Cliente obj = service.update(service.fromDTO(objDTO));
		return ResponseEntity.noContent().build();
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}	
		
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {		
		List<Cliente> lista = service.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok(listaDTO);
	}	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
				@RequestParam(value="page", defaultValue = "0") Integer numPage, 
				@RequestParam(value="linesPerPage", defaultValue = "10")Integer linesPerPages, 
				@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
				@RequestParam(value="direction", defaultValue = "ASC") String direction) {		
		
		Page<Cliente> lista = service.findPage(numPage, linesPerPages, orderBy, direction);
		Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok(listaDTO);
	}	
	
}
