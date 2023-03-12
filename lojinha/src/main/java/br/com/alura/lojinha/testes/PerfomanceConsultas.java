package br.com.alura.lojinha.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.lojinha.dao.CategoriaDAO;
import br.com.alura.lojinha.dao.ClienteDAO;
import br.com.alura.lojinha.dao.PedidoDAO;
import br.com.alura.lojinha.dao.ProdutoDAO;
import br.com.alura.lojinha.modelo.Categoria;
import br.com.alura.lojinha.modelo.Cliente;
import br.com.alura.lojinha.modelo.ItemPedido;
import br.com.alura.lojinha.modelo.Pedido;
import br.com.alura.lojinha.modelo.Produto;
import br.com.alura.lojinha.util.JPAUtil;

public class PerfomanceConsultas {

	public static void main(String[] args) {

		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();

		PedidoDAO pedidoDao = new PedidoDAO(em);
		Pedido pedido = pedidoDao.buscaPedidoComCliente(1l);
		System.out.println(pedido.getData());		
		
	}

	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");

		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("800"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook Pro 18.7 ", new BigDecimal("800"), informatica);

		Cliente cliente = new Cliente("Rodrigo", "123.456");

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, celular));
		pedido.adicionarItem(new ItemPedido(40, pedido, videogame));

		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(40, pedido2, macbook));

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		PedidoDAO pedidoDao = new PedidoDAO(em);

		em.getTransaction().begin(); // inicia a transação, pois não está automática

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(informatica);
		categoriaDao.cadastrar(videogames);

		clienteDao.cadastrar(cliente);

		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(macbook);
		produtoDao.cadastrar(videogame);

		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);

		em.getTransaction().commit(); // faz a transação para o bd
		em.close(); // encerra
	}

}
