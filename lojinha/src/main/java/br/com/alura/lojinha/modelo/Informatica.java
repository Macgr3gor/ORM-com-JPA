package br.com.alura.lojinha.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Informatica extends Produto{

	private String marca;
	private Integer modelo;
	
	public Informatica() {}

	public Informatica(String marca, Integer modelo) {
		super();
		this.marca = marca;
		this.modelo = modelo;
	}
  
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getModelo() {
		return modelo;
	}

	public void setModelo(Integer modelo) {
		this.modelo = modelo;
	}
	
	
}
