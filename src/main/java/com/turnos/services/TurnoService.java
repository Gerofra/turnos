package com.turnos.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turnos.entities.Horario;
import com.turnos.entities.Tipo;
import com.turnos.entities.Turno;
import com.turnos.entities.Usuario;
import com.turnos.errors.ErrorService;
import com.turnos.repos.HorarioRepo;
import com.turnos.repos.TurnoRepo;
import com.turnos.repos.UsuarioRepo;

@Service
public class TurnoService {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	TurnoRepo turnoRepo;
	@Autowired
	UsuarioRepo usuarioRepo;
	@Autowired
	DateService dateService;
	@Autowired
	HorarioService horarioService;
	@Autowired
	TipoService tipoService;

	@Transactional
	public Turno crearTurno(String dia, String mes, String anio, String hora, String tipo, String idEmpleado,
			String contacto, String mail, String mp_id) throws ErrorService, ParseException {

		try {
			Turno turno = new Turno();
			turno.setHora(hora);
			turno.setAnio(Integer.valueOf(anio));
			turno.setMes(Integer.valueOf(mes));
			turno.setDia(Integer.valueOf(dia));
			turno.setContacto(contacto);
			turno.setMail(mail);
			turno.setHorario_id(horarioService.traerHorario(dia, mes, anio, idEmpleado).getId());
			turno.setMp_id(mp_id);
			turno.setEmpleado(usuarioService.getById(idEmpleado));
			turno.setPrecio(tipoService.buscarPorId(tipo).getPrecio());
			turno.setDuracion(tipoService.buscarPorId(tipo).getDuracion());
			turno.setTipo(tipoService.buscarPorId(tipo).getNombre());
			return turnoRepo.save(turno);
		} catch (Exception e) {
			System.out.println("-------------- " + e.fillInStackTrace());
		}

		return null;
	}

	@Transactional
	public String verDia(String fecha) throws ErrorService, ParseException {
		String[] date = fecha.split("-");
		return date[2];
	}

	@Transactional
	public String verMes(String fecha) throws ErrorService, ParseException {
		String[] date = fecha.split("-");
		return date[1];
	}

	@Transactional
	public String verAnio(String fecha) throws ErrorService, ParseException {
		String[] date = fecha.split("-");
		return date[0];
	}

	/*
	 * public List<Turno> verTurnosEmpleado(String dia, String mes, String anio,
	 * String idEmpleado) throws Exception{ Optional<Usuario> respuesta =
	 * usuarioRepo.findById(idEmpleado); Usuario usuario = null; if
	 * (respuesta.isPresent()) { usuario = respuesta.get(); } Horario horario =
	 * horarioService.traerHorario(dia, mes, anio, idEmpleado); return
	 * horario.getTurnos(); }
	 */

	public List<Turno> turnosPorHorario(Long horario_id, String idEmpleado) throws Exception {
		Optional<Usuario> respuesta = usuarioRepo.findById(idEmpleado);
		Usuario usuario = null;
		if (respuesta.isPresent()) {
			usuario = respuesta.get();
		}
		List<Turno> turnos = turnoRepo.turnosPorHorario(horario_id, usuario);
		return turnos;
	}

	@Transactional
	public List<String> turnosDisponibles(String dia, String mes, String anio, String idEmpleado, String tipo)
			throws Exception {

		List<String> preHorarios = preHorarios(dia, mes, anio, idEmpleado);
		if (preHorarios == null) {
			return null;
		}

		Tipo t = tipoService.buscarPorId(tipo);
		Horario horario = horarioService.traerHorario(dia, mes, anio, idEmpleado);
		String[] dTipo = t.getDuracion().split(":");
		String[] dHasta = horario.getHasta().split(":");
		Map<String, String> turnosOcupados = turnosOcupados(turnosPorHorario(horario.getId(), idEmpleado));

		for (Map.Entry<String, String> entry : turnosOcupados.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			Integer h = Integer.valueOf(key) + Integer.valueOf(val);

			String hora = h.toString();
			preHorarios.remove(preHorarios.indexOf(key));
			preHorarios.add(hora);

		}

		Integer ultimaHora = Integer.valueOf(dHasta[0] + dHasta[1]) - Integer.valueOf(dTipo[0] + dTipo[1]);

		Collections.sort(preHorarios);

		System.out.println("------- " + ultimaHora);
		return preHorarios;
	}

	public List<Turno> verTurnos() {
		return turnoRepo.findAll();
	}

	public List<Turno> buscarTurnosEmpleado(String mes, String dia, String idEmpleado) {
		Usuario empleado = usuarioService.getById(idEmpleado);
		return turnoRepo.turnosEmpleado(Integer.valueOf(mes), Integer.valueOf(dia), empleado);
	}

	public List<Turno> buscarTurnosLibres(String mes, String dia, String anio, String idEmpleado) {
		Usuario empleado = usuarioService.getById(idEmpleado);

		List<Turno> turnos = turnoRepo.turnosLibres(Integer.valueOf(mes), Integer.valueOf(dia), Integer.valueOf(anio),
				empleado);
		turnos.sort(Comparator.comparing(Turno::getHora));

		return turnos;
	}

	public List<Turno> buscarTurnosEmpleadoMes(String mes, String idEmpleado) {
		Usuario empleado = usuarioService.getById(idEmpleado);
		return turnoRepo.turnosEmpleadoMes(Integer.valueOf(mes), empleado);
	}

	public boolean existeTurno(String mes, String dia, String hora, String idEmpleado) {
		Usuario empleado = usuarioService.getById(idEmpleado);

		if (turnoRepo.existeTurno(Integer.valueOf(mes), Integer.valueOf(dia), hora, empleado).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Map<String, String> turnosOcupados(List<Turno> turnosEmpleado) throws Exception {
		Map<String, String> horas = new LinkedHashMap<>();

		for (Turno turno : turnosEmpleado) {
			String[] d = turno.getDuracion().split(":");
			String duracion = d[0] + d[1];
			horas.put(turno.getHora(), duracion);
		}
		return horas;
	}

	public List<String> preHorarios(String dia, String mes, String anio, String idEmpleado) throws Exception {
		List<String> preHorarios = new ArrayList<>();
		Horario horario = horarioService.traerHorario(dia, mes, anio, idEmpleado);

		if (horario == null) {
			return null;
		}

		String[] ini = horario.getDesde().split(":");
		String[] fin = horario.getHasta().split(":");
		Integer desde = Integer.valueOf(ini[0] + ini[1]);
		Integer hasta = Integer.valueOf(fin[0] + fin[1]);

		for (int i = desde; i < hasta; i = i + 100) {
			preHorarios.add(Integer.toString(i));
		}

		return preHorarios;
	}

}
