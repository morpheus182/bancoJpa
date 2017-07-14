package com.beeva.bancoJpa.implementacion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beeva.bancoJpa.dao.CuentaDao;
import com.beeva.bancoJpa.modelo.Cuenta;

@Repository
public class CuentaImp extends CuentaDao{

	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public void agregarCuenta(Cuenta cuenta) {
		em.persist(cuenta);
	}
}