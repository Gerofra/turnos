package com.turnos.control;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.turnos.errors.ErrorService;
import com.turnos.services.DateService;
import com.turnos.services.TipoService;
import com.turnos.services.TurnoService;
import com.turnos.services.UsuarioService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TurnoService turnoService;

	@Autowired
	DateService dateService;
	
	@Autowired
	TipoService tipoService;
	
	@GetMapping("")
	public String admin(@RequestParam(required=false) String error, ModelMap model) {		
		return "admin.html";
	}
	
	@GetMapping("/panel_turnos")
	public String turnos(@RequestParam(required=false) String error, ModelMap model) {
		model.addAttribute("empleados", usuarioService.buscarEmpleados());		
		return "panel_turnos.html";
	}
	
	@GetMapping("/precios")
	public String precios(@RequestParam(required=false) String error, ModelMap model) {	
		model.addAttribute("tipos", tipoService.listaTipo());
		return "precios";
	}
	
	@PostMapping("/precios")
	public String agregarPrecio(@RequestParam String nombre, @RequestParam Integer precio, ModelMap model, RedirectAttributes redirect) {
		try {
			/*AVECES NO ENTRA AL ELSE*/
			if (!nombre.isEmpty() && nombre != null && precio >= 0 && precio != null) {
				tipoService.agregarTipo(nombre, precio);
				redirect.addFlashAttribute("tipos", tipoService.listaTipo());
				redirect.addFlashAttribute("success", "Se ha agregado correctamente.");
				return "redirect:/admin/precios";
			} else {
				redirect.addFlashAttribute("error", "Complete los campos correctamente.");
				return "redirect:/admin/precios";
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return "redirect:/admin/precios";
	}
	
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, ModelMap modelo, RedirectAttributes redirect) throws ErrorService {
        tipoService.eliminar(id);
        
        redirect.addFlashAttribute("success", "Se ha eliminado correctamente.");       
        return "redirect:/admin/precios";
    }

}
