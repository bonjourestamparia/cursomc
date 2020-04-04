package com.bonjourestamparia;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bonjourestamparia.domain.Categoria;
import com.bonjourestamparia.domain.Cidade;
import com.bonjourestamparia.domain.Cliente;
import com.bonjourestamparia.domain.Endereco;
import com.bonjourestamparia.domain.Estado;
import com.bonjourestamparia.domain.ItemPedido;
import com.bonjourestamparia.domain.Pagamento;
import com.bonjourestamparia.domain.PagamentoComBoleto;
import com.bonjourestamparia.domain.PagamentoComCartao;
import com.bonjourestamparia.domain.Pedido;
import com.bonjourestamparia.domain.Produto;
import com.bonjourestamparia.domain.enums.EstadoPagamento;
import com.bonjourestamparia.domain.enums.TipoCliente;
import com.bonjourestamparia.repositories.CategoriaRepository;
import com.bonjourestamparia.repositories.CidadeRepository;
import com.bonjourestamparia.repositories.ClienteRepository;
import com.bonjourestamparia.repositories.EnderecoRepository;
import com.bonjourestamparia.repositories.EstadoRepository;
import com.bonjourestamparia.repositories.ItemPedidoRepository;
import com.bonjourestamparia.repositories.PagamentoRepository;
import com.bonjourestamparia.repositories.PedidoRepository;
import com.bonjourestamparia.repositories.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
