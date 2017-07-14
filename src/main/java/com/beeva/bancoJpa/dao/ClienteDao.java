package com.beeva.bancoJpa.dao;

import com.beeva.bancoJpa.modelo.Cliente;
import com.beeva.bancoJpa.modelo.TipoCuenta;

public abstract class ClienteDao {
	public abstract void agregarCliente(Cliente cliente);
	public abstract String obtenerCliente(int numero);
	public abstract int obtenerNCliente(Cliente cliente);
}
