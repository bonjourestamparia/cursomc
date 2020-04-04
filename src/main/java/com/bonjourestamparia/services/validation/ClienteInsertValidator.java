package com.bonjourestamparia.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.domain.enums.TipoCliente;
import com.bonjourestamparia.dto.ClienteNewDTO;
import com.bonjourestamparia.repositories.ClienteRepository;
import com.bonjourestamparia.resources.exceptions.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{
	@Autowired
	ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDTO.getTipo().intValue() == TipoCliente.PESSOAFISICA.getCodigo()
				&& !CNP.isValidCPF(objDTO.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		
		if(objDTO.getTipo().intValue() == TipoCliente.PESSOAJURIDICA.getCodigo()
				&& !CNP.isValidCNPJ(objDTO.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		
		Cliente aux = repo.findByEmail(objDTO.getEmail());
		if(aux != null)
			list.add(new FieldMessage("email", "Email já existente"));
		
		list.forEach(fieldMessage -> {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		});
		
		return list.isEmpty();
	}
}
