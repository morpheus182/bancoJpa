package com.beeva.bancoJpa.implementacion;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beeva.bancoJpa.dao.BancoDao;
import com.beeva.bancoJpa.modelo.Banco;
import com.beeva.bancoJpa.modelo.Cliente;
import com.beeva.bancoJpa.modelo.Cuenta;

@Repository
public class BancoImp extends BancoDao{

	@PersistenceContext
	EntityManager em;
	
	@Override
	@Transactional
	public void agregarBanco(Banco banco) {
		em.persist(banco);		
	}

	@Override
	@Transactional
	public void deposito(double cantidad, Cliente cliente, Cuenta cuenta) {
		if(cliente.getIdcliente() == cuenta.getIdCliente()){
			double balanceCuenta = cuenta.getBalance();
			double newBalance = cantidad+balanceCuenta;
				cuenta.setBalance(newBalance);
				em.merge(cuenta);
				System.out.println("Deposito exitoso");
			}else{
				System.out.println("Fallo");				
			}		
	}

	@Override
	@Transactional
	public void retiro(double cantidad, Cuenta cuenta) {
		if(cuenta.getIdTipoCuenta() == 1){
			if(cuenta.getBalance()<5000){
				System.out.println("tu balnace es bajo no puedes retirar");
			}else{
			double retiro=cuenta.getBalance()- cantidad;			
			cuenta.setBalance(retiro);
			System.out.println("tu balance es de *"+retiro+"*");
			em.merge(cuenta);
			}
		}else if(cuenta.getIdTipoCuenta()==2){
			
			Calendar now = Calendar.getInstance();
			String[] strDays = new String[]{"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
			String hoy = strDays[now.get(Calendar.DAY_OF_WEEK) - 1] ;
					
			if(hoy == "Domingo" || hoy == "Sabado"){
				System.out.println("No puedes retirar por el dia "+ hoy );
			}else{
				double retiro=cuenta.getBalance() - cantidad;
				cuenta.setBalance(retiro);
				em.merge(cuenta);
					System.out.println("tu balance es de "+retiro);
			}
		}
		
	}
	
	
}
