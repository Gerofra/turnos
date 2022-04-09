package com.turnos.errors;

public class ErrorService extends Exception {

	public ErrorService(String msn) {
		super(msn);
	}
}
