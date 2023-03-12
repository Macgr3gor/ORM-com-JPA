package br.com.alura.lojinha.testes;

import java.math.BigDecimal;
import java.time.LocalDate;
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

public class TesteCriteria {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		List<Produto> lista = produtoDao.buscaPorParametrosComCriteria("PS5", null, null);
		List<Produto> lista2 = produtoDao.buscaPorParametros(null, null, LocalDate.now());
		System.out.println(lista);
		System.out.println(lista2);
		
	}

	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");

		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("800"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook Pro 18.7 ", new BigDecimal("800"), informatica);


		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);

		em.getTransaction().begin(); // inicia a transação, pois não está automática

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(informatica);
		categoriaDao.cadastrar(videogames);

		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(macbook);
		produtoDao.cadastrar(videogame);


		em.getTransaction().commit(); // faz a transação para o bd
		em.close(); // encerra
	}
	
}
