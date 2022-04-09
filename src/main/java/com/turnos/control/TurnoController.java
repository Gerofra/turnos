package com.turnos.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.turnos.entities.Tipo;
import com.turnos.services.DateService;
import com.turnos.services.HorarioService;
import com.turnos.services.TipoService;
import com.turnos.services.TurnoService;
import com.turnos.services.UsuarioService;

@Controller
@RequestMapping("/turnos")
public class TurnoController {

	@Autowired
	DateService dateService;

	@Autowired
	TurnoService turnoService;

	@Autowired
	HorarioService horarioService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	TipoService tipoService;

	@GetMapping("")
	public String portal(@RequestParam(required = false) String error, ModelMap model) {
		model.addAttribute("empleados", usuarioService.buscarEmpleados());
		return "turnos.html";
	}

	@GetMapping("/{idEmpleado}")
	public String empleadoElegido(@PathVariable("idEmpleado") String idEmpleado, ModelMap model,
			RedirectAttributes redirect) {

		try {
			model.addAttribute("empleadoElegido", usuarioService.getById(idEmpleado));
			model.addAttribute("tipos", tipoService.listaTipo());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "error";
		}
		return "turnos";

	}

	@GetMapping("/calendario")
	public String calendario(@RequestParam String idEmpleadoElegido, @RequestParam Long tipoId, ModelMap model,
			RedirectAttributes redirect) {

		try {
			model.addAttribute("idEmpleadoElegido", idEmpleadoElegido);
			model.addAttribute("tipo", tipoId);
			model.addAttribute("calendarioAnual", dateService.calendarioAnual());
			model.addAttribute("listaMeses", dateService.listaMeses());
			model.addAttribute("listaAnios", dateService.listaAnios());
			model.addAttribute("listaNombresMeses", dateService.listaNombresMeses());
			model.addAttribute("mesActual", dateService.mesActual());
			model.addAttribute("anioActual", dateService.anioActual());
			model.addAttribute("diaActual", dateService.diaActual());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "error";
		}
		return "turnos";

	}

	@GetMapping("/buscar")
	public String buscarTurnos(@RequestParam String id, @RequestParam String mes, @RequestParam String dia,
			@RequestParam String anio, @RequestParam String tipo, ModelMap modelo, RedirectAttributes redirect) {

		try {
			if (turnoService.turnosDisponibles(dia, mes, anio, id, tipo) == null
					|| turnoService.turnosDisponibles(dia, mes, anio, id, tipo).isEmpty()) {
				modelo.addAttribute("sinTurnos", "No hay turnos disponibles para el " + dia + "/" + mes + "/" + anio);
			} else {
				modelo.addAttribute("turnos", turnoService.turnosDisponibles(dia, mes, anio, id, tipo));
			}

			modelo.addAttribute("calendarioAnual", dateService.calendarioAnual());
			modelo.addAttribute("listaMeses", dateService.listaMeses());
			modelo.addAttribute("listaAnios", dateService.listaAnios());
			modelo.addAttribute("listaNombresMeses", dateService.listaNombresMeses());
			modelo.addAttribute("mesActual", dateService.mesActual());
			modelo.addAttribute("anioActual", dateService.anioActual());
			modelo.addAttribute("diaActual", dateService.diaActual());

			modelo.addAttribute("idEmpleadoElegido", id);
			modelo.addAttribute("tipo", tipo);
			modelo.addAttribute("mesElegido", mes);
			modelo.addAttribute("anioElegido", anio);
			modelo.addAttribute("diaElegido", dia);
			return "turnos";

		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "error";
		}

	}

	@PostMapping("/agregarTurno")
	public String agregarTurno(@RequestParam String id, @RequestParam String mes, @RequestParam String dia,
			@RequestParam String anio, @RequestParam String tipo, @RequestParam String hora, ModelMap modelo,
			RedirectAttributes redirect) {
		try {
			turnoService.crearTurno(dia, mes, anio, hora, tipo, id, "contacto", "mail", "mp_id");
			modelo.addAttribute("idEmpleadoElegido", id);
			modelo.addAttribute("tipo", tipo);
			modelo.addAttribute("calendarioAnual", dateService.calendarioAnual());
			modelo.addAttribute("listaMeses", dateService.listaMeses());
			modelo.addAttribute("listaAnios", dateService.listaAnios());
			modelo.addAttribute("listaNombresMeses", dateService.listaNombresMeses());
			modelo.addAttribute("mesActual", dateService.mesActual());
			modelo.addAttribute("anioActual", dateService.anioActual());
			modelo.addAttribute("diaActual", dateService.diaActual());

			return "turnos";

		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
			return "error";
		}

	}
}
