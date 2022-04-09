package com.turnos.services;

import java.util.List;
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

@Service
public class HorarioService {

	@Autowired
	HorarioRepo horarioRepo;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	TurnoService turnoService;
	@Autowired
	TipoService tipoService;

	@Transactional
	public void agregarHora(Integer dia, Integer mes, Integer anio, String desde, String hasta, String id)
			throws ErrorService {

		Horario horario = new Horario();
		horario.setDia(dia);
		horario.setMes(mes);
		horario.setAnio(anio);
		horario.setDesde(desde);
		horario.setHasta(hasta);
		horario.setEmpleado(usuarioService.getById(id));

		horarioRepo.save(horario);
	}

	@Transactional
	public boolean existeHorario(Integer dia, Integer mes, Integer anio, String id) throws ErrorService {
		Usuario empleado = usuarioService.getById(id);
		if (horarioRepo.existeHorario(mes, dia, anio, empleado) != null
				&& !horarioRepo.existeHorario(mes, dia, anio, empleado).isEmpty()) {
			return true;
		}
		return false;
	}

	@Transactional
	public List<Horario> buscarHorariosPorDia(String dia, String mes, String anio, String id) throws ErrorService {
		Usuario empleado = usuarioService.getById(id);
		return horarioRepo.horariosPorDia(Integer.valueOf(mes), Integer.valueOf(dia), Integer.valueOf(anio), empleado);
	}

	@Transactional
	public List<Horario> buscarHorariosPorMes(String mes, String anio, String id) throws ErrorService {
		Usuario empleado = usuarioService.getById(id);
		return horarioRepo.horariosPorMes(Integer.valueOf(mes), Integer.valueOf(anio), empleado);
	}

	@Transactional
	public Horario traerHorario(String dia, String mes, String anio, String id) throws Exception {
		Usuario empleado = usuarioService.getById(id);

		if (horarioRepo.existeHorario(Integer.valueOf(mes), Integer.valueOf(dia), Integer.valueOf(anio),
				empleado) == null
				|| horarioRepo
						.existeHorario(Integer.valueOf(mes), Integer.valueOf(dia), Integer.valueOf(anio), empleado)
						.size() == 0) {
			return null;
		} else {
			List<Horario> horarios = horarioRepo.existeHorario(Integer.valueOf(mes), Integer.valueOf(dia),
					Integer.valueOf(anio), empleado);
			return horarios.get(0);
		}

	}

	// Falta eliminar horario
}
