package com.bonjourestamparia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.dto.ClienteDTO;
import com.bonjourestamparia.repositories.ClienteRepository;
import com.bonjourestamparia.services.exceptions.DataIntegrityException;
import com.bonjourestamparia.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cli = repo.findById(id);
		return cli.orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrado"));
	}
	
	public Cliente insert(Cliente obj) {
		return repo.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir pois há entidades relacionadas");
		}
	}	
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer numPage, Integer linesPerPages, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(numPage, linesPerPages, Direction.valueOf(direction) , orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
