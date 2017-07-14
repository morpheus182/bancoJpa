package com.beeva.bancoJpa.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bancosclientes")
public class BancosClientes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idbancosclientes")	
	private int idBancoCliente;
	@Column(name="idcliente")
	private int idCliente;
	@Column(name="idbanco")
	private int idBanco;
	
	public int getIdBancoCliente() {
		return idBancoCliente;
	}
	public void setIdBancoCliente(int idBancoCliente) {
		this.idBancoCliente = idBancoCliente;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdBanco() {
		return idBanco;
	}
	public void setIdBanco(int idBanco) {
		this.idBanco = idBanco;
	}
}