package com.turnos.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mercadopago.exceptions.MPException;
import com.turnos.config.AuthenticationSystem;
import com.turnos.security.service.UserDetailsServiceImpl;

@Controller
public class InicioController {

	@Autowired
	UserDetailsServiceImpl detailsServiceImpl;

	@GetMapping("")
	public String inicio(@RequestParam(required = false) String error, ModelMap model) throws MPException {
		return "index.html";
	}

	@GetMapping("/mp")
	public String mp(@RequestParam(required = false) String error, ModelMap model) throws MPException {
		return "templatepagos.html";
	}

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
			ModelMap model) throws MPException {

		if (AuthenticationSystem.isLogged()) {
			return inicio(error, model);
		}

		if (error != null) {
			model.put("error", "Mail o contraseña incorrecta.");
		}
		if (logout != null) {
			model.put("logout", "Ha cerrado la sesión correctamente.");
		}
		return "login.html";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/portal")
	public String portal(@RequestParam(required = false) String error, ModelMap model) {

		if (AuthenticationSystem.isLogged()) {
			/*
			 * model.addAttribute("products", productoRepo.findAll());
			 */
		}
		return "index.html";
	}

}
