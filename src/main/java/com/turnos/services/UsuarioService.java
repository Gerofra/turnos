package com.turnos.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turnos.entities.Rol;
import com.turnos.entities.Usuario;
import com.turnos.enums.RolEnum;
import com.turnos.errors.ErrorService;
import com.turnos.repos.UsuarioRepo;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepo usuarioRepo;

	@Autowired
	private RolService rolService;

	@Transactional
	public void registrar(String name, String lastname, String mail, String password) throws ErrorService {

		validar(name, lastname, mail, password);

		Usuario usuario = new Usuario();
		usuario.setName(name);
		usuario.setLastname(lastname);
		usuario.setMail(mail);

		String encriptada = new BCryptPasswordEncoder().encode(password);
		usuario.setPassword(encriptada);

		usuarioRepo.save(usuario);

	}

	@Transactional
	public void modificar(String id, String name, String lastname, String mail, String password) throws ErrorService {

		validar(name, lastname, mail, password);

		Optional<Usuario> respuesta = usuarioRepo.findById(id);

		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			usuario.setLastname(lastname);
			usuario.setName(name);
			usuario.setMail(mail);

			String encriptada = new BCryptPasswordEncoder().encode(password);
			usuario.setPassword(encriptada);

			usuarioRepo.save(usuario);

		} else {
			throw new ErrorService("No se encontró el usuario solicitado.");
		}

	}

	private void validar(String name, String lastname, String mail, String password) throws ErrorService {

		System.out.println("Entró a validarse.");

		if (name == null || name.isEmpty()) {
			throw new ErrorService("El nombre del usuario no puede ser nulo.");
		}
		if (lastname == null || lastname.isEmpty()) {
			throw new ErrorService("El apellido del usuario no puede ser nulo.");
		}
		if (mail == null || mail.isEmpty()) {
			throw new ErrorService("El mail del usuario no puede ser nulo.");
		}
		if (password == null || password.isEmpty() || password.length() < 6) {
			throw new ErrorService("La clave del usuario no puede ser nulo y tiene que tener más de 6 dígitos.");
		}

		System.out.println("Terminó de validarse.");
	}

	public List<Usuario> buscarPorApellido(String lastname) {
		return usuarioRepo.listarPorApellido(lastname + "%");
	}

	public List<Usuario> buscarTodos() {
		return usuarioRepo.findAll();
	}

	public List<Usuario> buscarEmpleados() {
		Rol rolEmpleado = rolService.getByRolEnum(RolEnum.ROLE_EMPLEADO).get();
		Set<Rol> roles = new HashSet<>();
		roles.add(rolEmpleado);
		return usuarioRepo.findByRolesIn(roles);
	}

	public Usuario getById(String id) {
		Optional<Usuario> respuesta = usuarioRepo.findById(id);
		Usuario usuario = null;
		if (respuesta.isPresent()) {
			usuario = respuesta.get();
		}
		return usuario;
	}

	public Optional<Usuario> getByMail(String mail) {
		return usuarioRepo.findByMail(mail);
	}

	public boolean existById(String id) {
		return usuarioRepo.existsById(id);
	}

	public boolean existByMail(String mail) {
		return usuarioRepo.existsByMail(mail);
	}

}
