package com.bonjourestamparia.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.dto.ClienteDTO;
import com.bonjourestamparia.repositories.ClienteRepository;
import com.bonjourestamparia.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{
	@Autowired
	ClienteRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
	@Override
	public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
			
		Cliente aux = repo.findByEmail(objDTO.getEmail());
		if(aux != null && aux.getId().intValue() != uriId.intValue())
			list.add(new FieldMessage("email", "Email já existente"));
		
		list.forEach(fieldMessage -> {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		});
		
		return list.isEmpty();
	}
}
