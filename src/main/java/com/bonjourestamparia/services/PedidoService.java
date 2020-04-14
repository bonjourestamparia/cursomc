package com.bonjourestamparia.services;

import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonjourestamparia.domain.ItemPedido;
import com.bonjourestamparia.domain.PagamentoComBoleto;
import com.bonjourestamparia.domain.Pedido;
import com.bonjourestamparia.domain.enums.EstadoPagamento;
import com.bonjourestamparia.repositories.ItemPedidoRepository;
import com.bonjourestamparia.repositories.PagamentoRepository;
import com.bonjourestamparia.repositories.PedidoRepository;
import com.bonjourestamparia.repositories.ProdutoRepository;
import com.bonjourestamparia.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> ped = repo.findById(id);
		return ped.orElseThrow(()-> new ObjectNotFoundException("Pedido não encontrado"));
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setCliente(clienteService.buscar(obj.getCliente().getId()));
		obj.setInstante(Calendar.getInstance().getTime());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido itemPedido : obj.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setProduto(produtoService.buscar(itemPedido.getProduto().getId()));
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			itemPedido.setPedido(obj);
			
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(obj);
		
		return obj;
	}
}
