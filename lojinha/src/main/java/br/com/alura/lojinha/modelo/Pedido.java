package br.com.alura.lojinha.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data = LocalDate.now();
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
										// Relacionamentos terminados em ToOne sempre carregam no inicio da classe.
	@ManyToOne(fetch = FetchType.LAZY) //por padrão é .EAGER, para definir que o carregamento vai ser só no uso, colocar o .LAZY
	private Cliente cliente;           // se estiver no .EAGER ele carrega automaticamente pela outra classe, mesmo sem usar.
	
	//mappedBy, vincula a cardinalidade da tabela com outra, para não criar mais de uma (usar em caso de relacionamento bidirecional)
	//cascade ALL, faz com que a tabela tenha os mesmos dados, uma vez que estõa vinculadas e são referencia uma da outra.
	@OneToMany(mappedBy = "pedido" , cascade = CascadeType.ALL) 
	private List<ItemPedido> itens = new ArrayList<>(); //sempre já inicializar a lista

	public Pedido() {}
	
	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", data=" + data + ", valorTotal=" + valorTotal + ", cliente=" + cliente
				+ ", itens=" + itens + "]";
	}

	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
}
