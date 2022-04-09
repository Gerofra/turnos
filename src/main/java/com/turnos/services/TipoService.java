package com.turnos.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turnos.entities.Tipo;
import com.turnos.errors.ErrorService;
import com.turnos.repos.TipoRepo;

@Service
public class TipoService {

	@Autowired
	TipoRepo tipoRepo;

	@Transactional
	public void agregarTipo(String nombre, Integer precio, String duracion) throws ErrorService {

		Tipo tipo = new Tipo();
		tipo.setNombre(nombre);
		tipo.setDuracion(duracion);
		;
		tipo.setPrecio(precio);

		tipoRepo.save(tipo);
	}

	@Transactional
	public void eliminar(Long id) throws ErrorService {

		Optional<Tipo> respuesta = tipoRepo.findById(id);
		if (respuesta.isPresent()) {
			tipoRepo.delete(respuesta.get());
		}
	}

	@Transactional
	public List<Tipo> listaTipo() {
		return tipoRepo.findAll();
	}

	@Transactional
	public Tipo buscarPorId(String id) {
		Long id_tipo = Long.valueOf(id);
		Optional<Tipo> respuesta = tipoRepo.findById(id_tipo);
		if (respuesta.isPresent()) {
			return respuesta.get();
		}
		return null;
	}
}
