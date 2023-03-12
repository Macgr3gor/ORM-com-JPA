package br.com.alura.lojinha.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.lojinha.dao.CategoriaDAO;
import br.com.alura.lojinha.dao.ProdutoDAO;
import br.com.alura.lojinha.modelo.Categoria;
import br.com.alura.lojinha.modelo.Produto;
import br.com.alura.lojinha.util.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		cadastrarProduto();
		Long id = 1l;
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		
		Produto p = produtoDao.buscaPorId(id);
		System.out.println(p);
		
		//List<Produto> listaProdutos = produtoDao.buscarTodos();
		List<Produto> listaProdutos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
		listaProdutos.forEach(pro -> System.out.println(pro));
		
		BigDecimal precoProduto = produtoDao.buscarPreco("Xiaomi Redmi");
		System.out.println(precoProduto);
		
		String descricaoDoProduto = produtoDao.buscaDescricao("Xiaomi Redmi");
		System.out.println(descricaoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi","Muito legal",new BigDecimal("800"),celulares);
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);

		em.getTransaction().begin(); // inicia a transação, pois não está automática
		
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		
		em.getTransaction().commit(); //faz a transação para o bd
		em.close(); //encerra
	}
}
