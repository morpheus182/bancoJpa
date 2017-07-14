package com.beeva.bancoJpa;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.beeva.bancoJpa.modelo.Conexion;
import com.beeva.bancoJpa.modelo.Cuenta;
import com.beeva.bancoJpa.modelo.TipoCuenta;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

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
    	int totalClientes = 0;
    	int tipo;
    	String tipoDeCuenta;
    	String tipoDeBanco;
    	int tipoCuenta = 0;
    	int tipoBanco = 0;
    	boolean caracter = true;
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("core-context.xml");
    	ClienteDao clienteDao = (ClienteDao)context.getBean(ClienteImp.class);
    	TipoCuentaDao tipoCuentaDao = (TipoCuentaDao)context.getBean(TipoCuentaImp.class);
    	BancoDao bancoDao = (BancoDao)context.getBean(BancoImp.class);
    	//CuentaDao cuentaDao = (CuentaDao)context.getBean(CuentaDao.class); 
    	CuentaDao cuentaDao = (CuentaDao)context.getBean(CuentaImp.class);
    	BancosClientesDao bancosClientesDao= (BancosClientesDao)context.getBean(BancosClientesImp.class);
    	
    	Scanner in = new Scanner(System.in);
    	
    	java.util.Date fecha = new Date();
    
    	Conexion con = (Conexion)context.getBean("mongo");
    	con.prueba();
    	//MongoUtils mu = new MongoUtils(con);
    	
    	//MongoTemplate mt = (MongoTemplate)context.getBean("mongoTemplate");
    	DB db = con.getMongo().getDB("BancoLog");
    	
    	DBCollection tableCliente = db.getCollection("Cliente");
    	DBCollection tableCuenta = db.getCollection("Cuenta");
    	
    	BasicDBObject documentCliente = new BasicDBObject();
    	BasicDBObject documentCuenta = new BasicDBObject();
		
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
        
        //intentar leer la cantidad de clientes a ingresar
        do{
        System.out.println("Cuantos Clientes vas a ingresar??");
        try{
        	totalClientes = Integer.parseInt(in.next());
        }catch(NumberFormatException nfe){
        	System.out.println("Solamente puedes escribir números, vuelve a intentarlo");
        }
        } while(totalClientes == 0);
        	
        Cliente cliente[] = new Cliente[totalClientes];
        Cuenta cuenta[] = new Cuenta[totalClientes];
        BancosClientes bancosClientes[] = new BancosClientes[totalClientes];
        
        for(int i = 0; i < totalClientes; i++)
        {
        	cliente[i] = new Cliente();
        	cuenta[i] = new Cuenta();
        	bancosClientes[i] = new BancosClientes();
        	System.out.println("Agregar al cliente num: " + (i+1));
        	Pattern pat = Pattern.compile("[^0-9]{3,}");
        	
        	do{
        	System.out.println("Nombre: ");
            nombre = in.next();
            Matcher mat = pat.matcher(nombre);
            if(mat.find()){
            	cliente[i].setNombre(nombre);
            	caracter = true;
             }else{
                System.out.println("Nombre no válido");
                caracter = false;
           }
        	}while(caracter == false);

            //nombre = in.next();
        	//cliente[i].setNombre(nombre);
        	
        	do{
            	System.out.println("Apellido: ");
            	apellido = in.next();
                Matcher mat = pat.matcher(apellido);
                if(mat.find()){
                	cliente[i].setApellido(apellido);
                	caracter = true;
                 }else{
                    System.out.println("Apellido no válido");
                    caracter = false;
               }
            	}while(caracter == false);
        	
        	//leer tipo de banco
        	do{
        		System.out.println("tipo de banco (Bancomer o Banamex) del cliente: " + (i+1));
            	tipoDeBanco = in.next();
	        	//if(tipoDeBanco.equals("Bancomer") || tipoDeBanco.equals("bancomer") || tipoDeBanco.equals("BANCOMER"))
	        	if(tipoDeBanco.equalsIgnoreCase("bancomer"))
	        	{
	        		tipoBanco = 1;
	        	//} else if(tipoDeBanco.equals("Banamex") || tipoDeBanco.equals("banamex") || tipoDeBanco.equals("BANAMEX")){
	        	} else if(tipoDeBanco.equalsIgnoreCase("banamex")){
	        		tipoBanco = 2;
	        	} else {
	        		System.out.println("Banco no encontrado, intantalo otra vez");
	        		tipoBanco = 0;
	        		//i--;
	        		//continue;
	        	}
        	}while(tipoBanco == 0);
        	
        	//leer tipo de cuenta
        	do{
        	System.out.println("tipo de cuenta (Ahorro o Cheque) del cliente: " + (i+1));
        	tipoDeCuenta = in.next();
        	        	
        	//if(tipoDeCuenta.equals("Ahorro") || tipoDeCuenta.equals("ahorro") || tipoDeCuenta.equals("AHORRO"))
        	if(tipoDeCuenta.equalsIgnoreCase("Ahorro"))
        	{
        		tipoCuenta = 1;
        	//} else if(tipoDeCuenta.equals("Cheque") || tipoDeCuenta.equals("cheque") || tipoDeCuenta.equals("CHEQUE")){
        	} else if(tipoDeCuenta.equalsIgnoreCase("cheque")){
        		tipoCuenta = 2;
        	} else {
        		System.out.println("Tipo de Cuenta no encontrado, intantalo otra vez");
        		//i--;
        		//continue;
        	}
        	}while(tipoCuenta == 0);
        	
        	
        	try{
        	clienteDao.agregarCliente(cliente[i]);
        	
        	
        	documentCliente.put("mensaje", "Se inserto cliente");
        	documentCliente.put("fecha",fecha);
        	documentCliente.put("id", cliente[i].getIdcliente());
    		documentCliente.put("nombre", cliente[i].getNombre());
    		documentCliente.put("apellido", cliente[i].getApellido());
    		tableCliente.insert(documentCliente);
    		
        	cuenta[i].setBalance(0.0);
        	cuenta[i].setIdTipoCuenta(tipoCuenta);
        	cuenta[i].setIdCliente(cliente[i].getIdcliente());
        	
        	cuentaDao.agregarCuenta(cuenta[i]);
        	
        	documentCuenta.put("mensaje", "Se inserto cuenta");
        	documentCuenta.put("fecha",fecha);
        	documentCuenta.put("id", cuenta[i].getIdCuenta());
    		tableCuenta.insert(documentCuenta);
        	
        	bancosClientes[i].setIdBanco(tipoBanco);
        	bancosClientes[i].setIdCliente(cliente[i].getIdcliente());
        	
        	bancosClientesDao.agregarBancosClientes(bancosClientes[i]);
        	} catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        	
        	boolean salir = false;
            int opcion = 0;
             
            while(!salir){
            	System.out.println("Digita la opcion ");
            	System.out.println("1. Depositar");
            	System.out.println("2. Retirar");
            	System.out.println("3. Salir");
            	do{
            		try{
            			opcion = Integer.parseInt(in.next());
            		}catch(NumberFormatException nfe){
            			System.out.println("Solamente puedes escribir números, vuelve a intentarlo");
            			opcion = 0;
            			//salir = false;
            		}
            	} while(opcion == 0); 
               
            	switch(opcion){
	            	case 1:
	            		double deposito=0.0;
	            			try{
	            				System.out.println("Digita la cantidad a depositar");
	            				deposito = Double.parseDouble(in.next());
	            				bancoDao.deposito(deposito,cliente[i],cuenta[i]);
	            			}catch(NumberFormatException nfe){
	                			System.out.println("Solamente puedes escribir números, vuelve a intentarlo XD");
	                		}
	            		break;
	            	case 2:
	            		double retiro=0.0;
            			try{
            				System.out.println("Digita la cantidad a retirar");
            				retiro = Double.parseDouble(in.next());
            				bancoDao.retiro(retiro, cuenta[i]);
            			}catch(NumberFormatException nfe){
                			System.out.println("Solamente puedes escribir números, vuelve a intentarlo XD");
                		}
	            		break;
	            	case 3:
	            		salir=true;
	            		break;
	            	default:
	            		System.out.println("!!!!!! Solo números entre 1 y 3 !!!!!!!!!");
            	}
        	}
        }
    }//cierre for
}