package br.com.alura.lojinha.testes;

import javax.persistence.EntityManager;

import br.com.alura.lojinha.dao.CategoriaDAO;
import br.com.alura.lojinha.modelo.Categoria;
import br.com.alura.lojinha.util.JPAUtil;

public class TesteCRUD {

	public static void main(String[] args) {
		
		Categoria celulares = new Categoria("CELULARES");
		
		EntityManager em = JPAUtil.getEntityManager();
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		
		em.getTransaction().begin(); //inicia
		
		em.persist(celulares); 
		celulares.setNome("XPTO"); //mesmo após o persist(), ele ainda é alterado, pois está em estado de MANAGED,
								  // continua assim até que o commit() ou flush() seja feito.
		
		em.flush(); // envia para o BD
		em.clear(); // apenas limpa e continua o processo
		
		celulares = em.merge(celulares); // retorna para o modo de edição
		celulares.setNome("SMARTPHONE");
		em.flush();
		
		em.clear(); // limpa o processo, e pode continuar.
		
		em.remove(celulares);
		em.flush();
		
	}
}
