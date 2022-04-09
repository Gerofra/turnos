package com.turnos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.turnos.entities.Rol;
import com.turnos.enums.RolEnum;
import com.turnos.services.RolService;

@Service
public class CreateRoles implements CommandLineRunner {

	@Autowired
	RolService rolService;

	@Override
	public void run(String... args) throws Exception {

		/*
		 * AGREGAN LOS ROLES A LA BASE DE DATOS A TRAVES DE LINEA DE COMANDO Rol
		 * rolAdmin = new Rol(RolEnum.ROLE_ADMIN); Rol rolPropietario = new
		 * Rol(RolEnum.ROLE_PROPIETARIO); Rol rolEmpleado = new
		 * Rol(RolEnum.ROLE_EMPLEADO); Rol rolUser = new Rol(RolEnum.ROLE_USUARIO);
		 * 
		 * rolService.save(rolAdmin); rolService.save(rolPropietario);
		 * rolService.save(rolEmpleado); rolService.save(rolUser);
		 */

	}
}
