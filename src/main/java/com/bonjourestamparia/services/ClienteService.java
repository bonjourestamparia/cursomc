package com.bonjourestamparia.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.Cidade;
import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.domain.Endereco;
import com.bonjourestamparia.domain.enums.TipoCliente;
import com.bonjourestamparia.dto.ClienteDTO;
import com.bonjourestamparia.dto.ClienteNewDTO;
import com.bonjourestamparia.repositories.ClienteRepository;
import com.bonjourestamparia.repositories.EnderecoRepository;
import com.bonjourestamparia.services.exceptions.DataIntegrityException;
import com.bonjourestamparia.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cli = repo.findById(id);
		return cli.orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrado"));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli =  new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), bCryptPasswordEncoder.encode(objDTO.getSenha()));
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getCep(), cli, new Cidade(objDTO.getCidadeId()));
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null)
			cli.getTelefones().add(objDTO.getTelefone2());
		
		if(objDTO.getTelefone3() != null)
			cli.getTelefones().add(objDTO.getTelefone3());
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
