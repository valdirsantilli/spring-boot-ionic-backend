package com.ecommerce.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.domain.ItemPedido;
import com.ecommerce.domain.PagamentoComBoleto;
import com.ecommerce.domain.Pedido;
import com.ecommerce.domain.enums.EstadoPagamento;
import com.ecommerce.repositories.ItemPedidoRepository;
import com.ecommerce.repositories.PagamentoRepository;
import com.ecommerce.repositories.PedidoRepository;
import com.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	@Autowired
	private PagamentoRepository pagtoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private BoletoService boletoService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: "+ Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(LocalDateTime.now());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagtoRepository.save(obj.getPagamento());
		for (ItemPedido i : obj.getItens()) {
			i.setDesconto(0.00);
			i.setPreco(produtoService.find(i.getProduto().getId()).getPreco());
			i.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}