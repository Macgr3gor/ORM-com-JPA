package br.com.alura.lojinha.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.lojinha.modelo.Produto;

public class ProdutoDAO {
	
	private EntityManager em;
	
	public ProdutoDAO(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}
	
	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}
	
	public void remover(Produto produto) {
		produto = em.merge(produto);
		this.em.remove(produto);
	}
	
	public Produto buscaPorId(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos(){
		String jpql = "SELECT p FROM Produto p"; //codigo sql para pegar todos os produtos
		return em.createQuery(jpql, Produto.class).getResultList(); //transforma o resultado da query em lista
	}
	
	public List<Produto> buscarPorNome(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome"; //codigo sql para pegar todos os produtos
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList(); //transforma o resultado da query em lista
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome"; //codigo sql para pegar todos os produtos
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList(); //transforma o resultado da query em lista
	}
	
	public BigDecimal buscarPreco(String nome){
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome"; //codigo sql para pegar todos os produtos
		return (BigDecimal) em.createQuery(jpql, BigDecimal.class)
				.setParameter("nome", nome)
				.getSingleResult();    
	}
	
	public String buscaDescricao(String nome) {
		String jpql = "SELECT p.descricao FROM Produto p WHERE p.nome = :nome";
		
		return (String) em.createQuery(jpql, String.class)
				.setParameter("nome", nome)
				.getSingleResult();
	}
	
	//COM JPQL
	public List<Produto> buscaPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro){ 
		
		String jpql = "SELECT p FROM Produto p WHERE 1=1 "; //1=1 pra poder aceitar os AND abaixo
		
		if(nome != null && !nome.trim().isEmpty()) //verifica se não está vazio
			jpql += " AND p.nome = :nome"; //add na query
		if(preco != null  ) //verifica se não está vazio
			jpql += " AND p.preco = :preco";
		if(dataCadastro != null  ) //verifica se não está vazio
			jpql += " AND p.dataCadastro = :dataCadastro";
		
		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
		
		if(nome != null && !nome.trim().isEmpty())
			query.setParameter("nome", nome);
		if(preco != null  )
			query.setParameter("preco", preco);
		if(dataCadastro != null  )
			query.setParameter("dataCadastro", dataCadastro);
		
		return query.getResultList();
		
	}
	
	//COM CRITERIA API
	public List<Produto> buscaPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class); //Define a classe a ser usada, serve como o SELECT do SQL
		
		Predicate filtros = builder.and(); //serve como o AND do SQL
		
		if(nome != null && !nome.trim().isEmpty()) //verifica se não está vazio
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome)); //adiciona na builder que é o SQL a ser feito a consulta no BD 
		if(preco != null)
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		if(dataCadastro != null)
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		
		query.where(filtros); //por fim, é adicionado tudo a SQL final de consulta
		
	
		return em.createQuery(query).getResultList(); //pega o resultado e o trasnforma em uma lista
		
	}
	
}
