package br.com.alura.lojinha.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.lojinha.modelo.Pedido;
import br.com.alura.lojinha.vo.RelatorioDeVendasVo;

public class PedidoDAO {
	
	private EntityManager em;
	
	public PedidoDAO(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		String jqpl = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jqpl, BigDecimal.class)
				.getSingleResult();
	}
	
	public List<RelatorioDeVendasVo> relatorioVenda(){
		String jqpl = "SELECT new br.com.alura.lojinha.vo.RelatorioDeVendasVo( "
				+ "produto.nome,"
				+ "SUM(item.quantidade),"
				+ "MAX(pedido.data))"
				+ "FROM Pedido pedido "
				+ "JOIN pedido.itens item "
				+ "JOIN item.produto produto "
				+ "GROUP BY produto.nome "
				+ "ORDER BY item.quantidade DESC ";
		return em.createQuery(jqpl, RelatorioDeVendasVo.class)
				.getResultList();
	}
	
	public Pedido buscaPorId(long id) {
		return this.em.find(Pedido.class, id);
	}
	
	public Pedido buscaPedidoComCliente(Long id) {
		//JOIN FETCH coloca pra carregar a entidade/classe que possívelmente poderá ser usada no futuro "query planejada"
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class )
				.setParameter("id", id)
				.getSingleResult();
	}
	
}
