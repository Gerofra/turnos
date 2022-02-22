package com.turnos.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turnos.entities.Rol;
import com.turnos.entities.Usuario;
import com.turnos.enums.RolEnum;
import com.turnos.repos.UsuarioRepo;
import com.turnos.services.RolService;
import com.turnos.services.UsuarioService;

@Service
public class CreateAdmin implements CommandLineRunner {

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	UsuarioRepo usuarioRepo;

	@Autowired
	RolService rolService;

	@Override
	public void run(String... args) throws Exception {
				
		
		/* ASIGNO ROL ADINISTRADOR		
		Usuario usuario = new Usuario();
		String encriptada = new BCryptPasswordEncoder().encode("admin");
		
		usuario.setName("Gerónimo");
		usuario.setLastname("Pericoli");
		usuario.setMail("geronimopericoli@gmail.com");
		usuario.setPassword(encriptada);
		
		Rol rolAdmin = rolService.getByRolEnum(RolEnum.ROLE_ADMIN).get();
		Rol rolPropietario = rolService.getByRolEnum(RolEnum.ROLE_PROPIETARIO).get();
		Rol rolUser = rolService.getByRolEnum(RolEnum.ROLE_USUARIO).get();
		
		Set<Rol> roles = new HashSet<>();
		roles.add(rolUser);
		roles.add(rolPropietario);
		roles.add(rolAdmin);
		usuario.setRoles(roles);
		
		usuarioRepo.save(usuario);
		*/
		

	}
}
