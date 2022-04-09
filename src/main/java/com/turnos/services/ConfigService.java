package com.turnos.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turnos.entities.Config;
import com.turnos.errors.ErrorService;
import com.turnos.repos.ConfigRepo;

@Service
public class ConfigService {

	@Autowired
	ConfigRepo configRepo;

	@Transactional
	public void editarConfig(String nombre, String descrip, String calle, String altura, String referencia,
			String localidad, String abre, String cierra) throws ErrorService {

		Config config = traerConfig();

		if (nombre != null && !nombre.isEmpty()) {
			config.setNombre(nombre);
		}
		if (descrip != null && !descrip.isEmpty()) {
			config.setDescrip(descrip);
		}
		if (calle != null && !calle.isEmpty()) {
			config.setCalle(calle);
		}
		if (altura != null && !altura.isEmpty()) {
			config.setAltura(altura);
		}
		if (referencia != null && !referencia.isEmpty()) {
			config.setReferencia(referencia);
		}
		if (localidad != null && !localidad.isEmpty()) {
			config.setLocalidad(localidad);
		}
		if (abre != null && !abre.isEmpty()) {
			config.setAbre(abre);
		}
		if (cierra != null && !cierra.isEmpty()) {
			config.setCierra(cierra);
		}
		configRepo.save(config);
	}

	@Transactional
	public Config traerConfig() {
		return configRepo.findAll().get(0);
	}
}
