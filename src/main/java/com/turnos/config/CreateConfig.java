package com.turnos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.turnos.entities.Config;
import com.turnos.repos.ConfigRepo;

@Service
public class CreateConfig implements CommandLineRunner {

	@Autowired
	ConfigRepo configRepo;

	@Override
	public void run(String... args) throws Exception {

		if (configRepo.findAll().size() == 0) {
			Config config = new Config();
			config.setNombre("");
			config.setDescrip("");
			config.setCalle("");
			config.setAltura("");
			config.setReferencia("");
			config.setLocalidad("");
			config.setAbre("10:00");
			config.setCierra("20:00");

			configRepo.save(config);
		}

	}

}
