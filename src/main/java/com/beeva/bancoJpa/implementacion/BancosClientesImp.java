package com.beeva.bancoJpa.implementacion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beeva.bancoJpa.dao.BancosClientesDao;
import com.beeva.bancoJpa.modelo.BancosClientes;

@Repository
public class BancosClientesImp extends BancosClientesDao{

	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public void agregarBancosClientes(BancosClientes bancosClientes)
	{
		em.persist(bancosClientes);
	}
}
