package com.turnos.entities;

public class Pago {

	private String id;
	private String nombre;
	
	
	
	public Pago(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	
}
