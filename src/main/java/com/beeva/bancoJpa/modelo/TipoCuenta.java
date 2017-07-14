package com.beeva.bancoJpa.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipocuenta")
public class TipoCuenta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idtipocuenta")
	private int idTipoCuenta;
	private String nombre;
	
	public int getIdTipoCuenta() {
		return idTipoCuenta;
	}
	public void setId(int idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}