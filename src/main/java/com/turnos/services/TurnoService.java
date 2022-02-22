package com.turnos.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.turnos.entities.Turno;
import com.turnos.entities.Usuario;
import com.turnos.errors.ErrorService;
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

	@Transactional
	public void agregarTurno(String fecha, String hora, String idEmpleado) throws ErrorService, ParseException {
		
		String[] date = fecha.split("-");
		
		Turno turno = new Turno();		
		turno.setHora(hora);
		turno.setAnio(Integer.valueOf(date[0]));
		turno.setMes(Integer.valueOf(date[1]));
		turno.setDia(Integer.valueOf(date[2]));
		turno.setEmpleado(usuarioService.getById(idEmpleado));

		turnoRepo.save(turno);	
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
	
	
	public List<Turno> verTurnosEmpleado(String idEmpleado){
		Optional<Usuario> respuesta = usuarioRepo.findById(idEmpleado);
		Usuario usuario = null;
		if	(respuesta.isPresent()) {
			usuario = respuesta.get();
		}
		
		return turnoRepo.buscarPorEmpleado(usuario);
	}
	
	public List<Turno> verTurnos(){
		return turnoRepo.findAll();
	}
	
	public List<Turno> buscarTurnosEmpleado(String mes, String dia, String idEmpleado){
		Usuario empleado = usuarioService.getById(idEmpleado);
		return turnoRepo.turnosEmpleado(Integer.valueOf(mes), Integer.valueOf(dia), empleado);
	}
	
	public List<Turno> buscarTurnosLibres(String mes, String dia, String anio, String idEmpleado){
		Usuario empleado = usuarioService.getById(idEmpleado);
		
		List<Turno> turnos = turnoRepo.turnosLibres(Integer.valueOf(mes), Integer.valueOf(dia), Integer.valueOf(anio), empleado);		
		turnos.sort(Comparator.comparing(Turno::getHora));
		
		return turnos;
	}
	
	public List<Turno> buscarTurnosEmpleadoMes(String mes, String idEmpleado){
		Usuario empleado = usuarioService.getById(idEmpleado);
		return turnoRepo.turnosEmpleadoMes(Integer.valueOf(mes), empleado);
	}
	
	public boolean existeTurno(String mes, String dia, String hora, String idEmpleado) {
		Usuario empleado = usuarioService.getById(idEmpleado);
		
		if (turnoRepo.existeTurno(Integer.valueOf(mes), Integer.valueOf(dia), hora, empleado).size() > 0 ) {
			return true;
		} else {
			return false;
		}
	}
}
