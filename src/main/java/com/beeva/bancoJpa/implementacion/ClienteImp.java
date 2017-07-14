package com.beeva.bancoJpa.implementacion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beeva.bancoJpa.dao.ClienteDao;
import com.beeva.bancoJpa.modelo.Cliente;

@Repository
public class ClienteImp extends ClienteDao{

	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public void agregarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
			em.persist(cliente);
	}

	@Override
	@Transactional
	public String obtenerCliente(int numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public int obtenerNCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return 0;
	}
}