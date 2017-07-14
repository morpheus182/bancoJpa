package com.beeva.bancoJpa;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.beeva.bancoJpa.dao.BancoDao;
import com.beeva.bancoJpa.dao.BancosClientesDao;
import com.beeva.bancoJpa.dao.ClienteDao;
import com.beeva.bancoJpa.dao.CuentaDao;
import com.beeva.bancoJpa.dao.TipoCuentaDao;
import com.beeva.bancoJpa.implementacion.BancoImp;
import com.beeva.bancoJpa.implementacion.BancosClientesImp;
import com.beeva.bancoJpa.implementacion.ClienteImp;
import com.beeva.bancoJpa.implementacion.CuentaImp;
import com.beeva.bancoJpa.implementacion.TipoCuentaImp;
import com.beeva.bancoJpa.modelo.Banco;
import com.beeva.bancoJpa.modelo.BancosClientes;
import com.beeva.bancoJpa.modelo.Cliente;
import com.beeva.bancoJpa.modelo.Cuenta;
import com.beeva.bancoJpa.modelo.TipoCuenta;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String nombre;
    	String apellido;
    	int totalClientes;
    	int tipo;
    	String tipoDeCuenta;
    	String tipoDeBanco;
    	int tipoCuenta = 1;
    	int tipoBanco = 1;
    	
    	
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("core-context.xml");
    	ClienteDao clienteDao = (ClienteDao)context.getBean(ClienteImp.class);
    	TipoCuentaDao tipoCuentaDao = (TipoCuentaDao)context.getBean(TipoCuentaImp.class);
    	BancoDao bancoDao = (BancoDao)context.getBean(BancoImp.class);
    	//CuentaDao cuentaDao = (CuentaDao)context.getBean(CuentaDao.class); 
    	CuentaDao cuentaDao = (CuentaDao)context.getBean(CuentaImp.class);
    	BancosClientesDao bancosClientesDao= (BancosClientesDao)context.getBean(BancosClientesImp.class);
    	
    	Scanner in = new Scanner(System.in);
    	
    	TipoCuenta tipoCuenta1 = new TipoCuenta();
    	TipoCuenta tipoCuenta2 = new TipoCuenta();
    	
    	Banco banco1 = new Banco();
    	Banco banco2 = new Banco();
    	
    	
    	
        tipoCuenta1.setNombre("Ahorro");
        tipoCuenta2.setNombre("Cheque");
        banco1.setNombre("Bancomer");
        banco2.setNombre("Banamex");
    	
        //agregar Tipos de cuentas
        tipoCuentaDao.agregarTipoCuenta(tipoCuenta1);
        tipoCuentaDao.agregarTipoCuenta(tipoCuenta2);
        //agregar bancos
        bancoDao.agregarBanco(banco1);
        bancoDao.agregarBanco(banco2);
        
        System.out.println("Cuantos Clientes vas a ingresar??");
        totalClientes = in.nextInt();
        Cliente cliente[] = new Cliente[totalClientes];
        Cuenta cuenta[] = new Cuenta[totalClientes];
        BancosClientes bancosClientes[] = new BancosClientes[totalClientes];
        for(int i = 0; i < totalClientes; i++)
        {
        	cliente[i] = new Cliente();
        	cuenta[i] = new Cuenta();
        	bancosClientes[i] = new BancosClientes();
        	System.out.println("Agregar al cliente num: " + (i+1));
            System.out.println("Nombre: ");
        	nombre = in.next();
        	cliente[i].setNombre(nombre);
        	
        	System.out.println("Apellido: ");
        	apellido = in.next();
        	cliente[i].setApellido(apellido);
        	
        	System.out.println("tipo de banco (Bancomer o Banamex): ");
        	tipoDeBanco = in.next();
        	
        	if(tipoDeBanco.equals("Bancomer") || tipoDeBanco.equals("bancomer"))
        	{
        		tipoBanco = 1;
        	} else if(tipoDeBanco.equals("Banamex") || tipoDeBanco.equals("banamex")) {
        		tipoBanco = 2;
        	} else {
        		System.out.println("Banco no encontrado, intantalo otra vez");
        		i--;
        		continue;
        	}
        	
        	System.out.println("tipo de cuenta (Ahorro o Cheque): ");
        	tipoDeCuenta = in.next();
        	        	
        	if(tipoDeCuenta.equals("Ahorro") || tipoDeCuenta.equals("ahorro"))
        	{
        		tipoCuenta = 1;
        	} else if(tipoDeCuenta.equals("Cheque") || tipoDeCuenta.equals("cheque")){
        		tipoCuenta = 2;
        	} else {
        		System.out.println("Tipo de Cuenta no encontrado, intantalo otra vez");
        		i--;
        		continue;
        	}
        	try{
        	clienteDao.agregarCliente(cliente[i]);
        	
        	cuenta[i].setBalance(0.0);
        	cuenta[i].setIdTipoCuenta(tipoCuenta);
        	cuenta[i].setIdCliente(cliente[i].getIdcliente());
        	
        	cuentaDao.agregarCuenta(cuenta[i]);
        	
        	bancosClientes[i].setIdBanco(tipoBanco);
        	bancosClientes[i].setIdCliente(cliente[i].getIdcliente());
        	
        	bancosClientesDao.agregarBancosClientes(bancosClientes[i]);
        	} catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        	boolean salir = false;
            int opcion; //Guardaremos la opcion del usuario
             
            while(!salir){
           	 System.out.println("Digita la opcion ");
                System.out.println("1. Depositar");
                System.out.println("2. Retirar");
                System.out.println("3. Salir");
                                
                opcion = in.nextInt();
                
                switch(opcion){
                case 1:
               	 System.out.println("cuanto vas a depositar?");
            		bancoDao.deposito(in.nextDouble(),cliente[i],cuenta[i]);
                    break;
                case 2:
               	 System.out.println("cuanto vas a retirar?");
               	 bancoDao.retiro(in.nextDouble(), cuenta[i]);
                    break;
                 case 3:
                    salir=true;
                    break;
                 default:
                    System.out.println("!!!!!! Solo números entre 1 y 3 !!!!!!!!!");
                }
            }
        }//cierre for
    }
    private static boolean isNumeric(String next) {
		try{
			
	        int num = Integer.parseInt( next );
	        return true;
	    }
	    catch( NumberFormatException err ){
	        return false;
	    }
	}
}