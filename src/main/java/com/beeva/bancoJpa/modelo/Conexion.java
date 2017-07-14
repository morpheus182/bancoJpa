package com.beeva.bancoJpa.modelo;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class Conexion {

	private String type;
	private int puerto;
	MongoClient mongo;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	
	public MongoClient getMongo() {
		return mongo;
	}
	public void setMongo(MongoClient mongo) {
		this.mongo = mongo;
	}
	public void prueba(){
		try {
			mongo = new MongoClient(getType(),getPuerto());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}	
}