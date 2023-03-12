package br.com.alura.lojinha.dao;

import javax.persistence.EntityManager;

import br.com.alura.lojinha.modelo.Cliente;
import br.com.alura.lojinha.modelo.Produto;

public class ClienteDAO {
	
	private EntityManager em;
	
	public ClienteDAO(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Cliente cliente) {
		this.em.persist(cliente);
	}
	
	public Cliente buscaPorId(Long id) {
		return em.find(Cliente.class, id);
	}
}
