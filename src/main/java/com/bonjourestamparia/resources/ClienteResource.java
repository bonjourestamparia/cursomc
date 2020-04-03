package com.bonjourestamparia.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.dto.ClienteDTO;
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
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		objDTO.setId(id);
		Cliente obj = service.update(service.fromDTO(objDTO));
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}	
		
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {		
		List<Cliente> lista = service.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok(listaDTO);
	}	
	
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
