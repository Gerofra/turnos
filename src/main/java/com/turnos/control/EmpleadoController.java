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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.turnos.errors.ErrorService;
import com.turnos.services.DateService;
import com.turnos.services.TurnoService;
import com.turnos.services.UsuarioService;

@Controller
@RequestMapping("/admin/panel_turnos/empleado")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class EmpleadoController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TurnoService turnoService;

	@Autowired
	DateService dateService;
	
	@GetMapping("/")
	public String panelTurnos(@RequestParam(required=false) String error, ModelMap model) {
		model.addAttribute("turnos", turnoService.verTurnos());		
		return "empleado";
	}
	
	@GetMapping("/{id}")
	public String verEmpleado(@PathVariable("id") String id, ModelMap model, RedirectAttributes redirect) {
				
		model.addAttribute("empleado", usuarioService.getById(id));
		model.addAttribute("fechaActual", dateService.fechaActual());
		
		return "empleado";
	}
	
	@PostMapping("/{id}")
    public String agregarTurno(@PathVariable("id") String id, ModelMap modelo, @RequestParam String fecha, @RequestParam String hora, @RequestParam(required = true, defaultValue = "null") String idEmpleado, RedirectAttributes redirect) throws ErrorService, ParseException {
		
        try {
        	if (fecha != null && !fecha.isEmpty() && hora != null) {
    	    	
        		if (!turnoService.existeTurno(turnoService.verMes(fecha), turnoService.verDia(fecha), hora, idEmpleado)) {
        			 
        			 turnoService.agregarTurno(fecha, hora, idEmpleado);	
        			 redirect.addFlashAttribute("fechaActual", dateService.fechaActual());
        			 redirect.addFlashAttribute("success", "Se ha agregado el turno correctamente.");
        		} else {
        			 redirect.addFlashAttribute("error", "El turno ya existe.");
        		}

    	    	return "redirect:/admin/panel_turnos/empleado/{id}";
  	    	
        		
        	} else {
	    		redirect.addFlashAttribute("error", "Debe completar todos los campos.");
	    		return "redirect:/admin";
        	}

        } catch (Exception e) {
        	modelo.addAttribute("error", e.getMessage());
            return "admin";
        }
    }
	
	@GetMapping("/turnos/{id}")
    public String verTurnos(@PathVariable("id") String id, @RequestParam String mes, @RequestParam String dia, @RequestParam String idEmpleado, ModelMap modelo, RedirectAttributes redirect) {

		if (dia.equalsIgnoreCase("todos")) {
	    	redirect.addFlashAttribute("turnos", turnoService.buscarTurnosEmpleadoMes(mes, idEmpleado));
	    	redirect.addFlashAttribute("success", "");
		} else {
			redirect.addFlashAttribute("turnos", turnoService.buscarTurnosEmpleado(mes, dia, idEmpleado));
		}
		return "redirect:/admin/panel_turnos/empleado/{id}";
	
    }
}
