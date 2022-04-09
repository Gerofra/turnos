package com.turnos.control;

import java.text.ParseException;
import java.util.Iterator;

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
import com.turnos.services.HorarioService;
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
	HorarioService horarioService;

	@Autowired
	DateService dateService;

	@GetMapping("/")
	public String panelTurnos(@RequestParam(required = false) String error, ModelMap model) {
		model.addAttribute("turnos", turnoService.verTurnos());
		return "empleado";
	}

	@GetMapping("/{id}")
	public String verEmpleado(@PathVariable("id") String id, ModelMap model, RedirectAttributes redirect) {

		model.addAttribute("empleado", usuarioService.getById(id));
		model.addAttribute("fechaActual", dateService.fechaActual());
		model.addAttribute("anioActual", dateService.anioActual());
		return "empleado";
	}

	@PostMapping("/{id}")
	public String agregarHorario(@PathVariable("id") String id, ModelMap modelo, @RequestParam String fecha,
			@RequestParam String desde, @RequestParam String hasta, @RequestParam String cant,
			@RequestParam(required = true, defaultValue = "null") String idEmpleado, RedirectAttributes redirect)
			throws ErrorService, ParseException {

		String[] parts = fecha.split("-");
		Integer anio = Integer.valueOf(parts[0]);
		Integer mes = Integer.valueOf(parts[1]);
		Integer dia = Integer.valueOf(parts[2]);
		try {
			if (fecha != null && !fecha.isEmpty() && fecha != null) {
				if (!horarioService.existeHorario(dia, mes, anio, idEmpleado)) {

					for (int i = 0; i < Integer.valueOf(cant); i++) {
						horarioService.agregarHora(dia, mes, anio, desde, hasta, idEmpleado);

						if (dateService.ultimoDiaDelMes(dia, mes, anio) && mes == 12) {
							dia = 0;
							mes = 1;
							anio++;
						} else if (dateService.ultimoDiaDelMes(dia, mes, anio)) {
							dia = 0;
							mes++;
						}
						dia++;
					}
					redirect.addFlashAttribute("fechaActual", dateService.fechaActual());
					redirect.addFlashAttribute("success", "Se ha agregado el turno correctamente.");
				} else {
					redirect.addFlashAttribute("error", "El horario ya existe.");
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
	public String verTurnos(@PathVariable("id") String id, @RequestParam String mes, @RequestParam String dia,
			@RequestParam String anio, @RequestParam String idEmpleado, ModelMap modelo, RedirectAttributes redirect)
			throws ErrorService {

		System.out.println("--- " + horarioService.buscarHorariosPorMes(mes, anio, idEmpleado));

		if (dia.equalsIgnoreCase("todos")) {
			redirect.addFlashAttribute("horarios", horarioService.buscarHorariosPorMes(mes, anio, idEmpleado));
			redirect.addFlashAttribute("success", "");
		} else {
			redirect.addFlashAttribute("horarios", horarioService.buscarHorariosPorDia(dia, mes, anio, idEmpleado));
		}
		return "redirect:/admin/panel_turnos/empleado/{id}";

	}
}
