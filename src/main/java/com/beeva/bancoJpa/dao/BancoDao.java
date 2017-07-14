package com.beeva.bancoJpa.dao;

import com.beeva.bancoJpa.modelo.Banco;
import com.beeva.bancoJpa.modelo.Cliente;
import com.beeva.bancoJpa.modelo.Cuenta;

public abstract class BancoDao {
	public abstract void agregarBanco(Banco banco);
	public abstract void deposito(double cantidad, Cliente cliente, Cuenta cuenta);
	public abstract void retiro(double cantidad, Cuenta cuenta);
}
