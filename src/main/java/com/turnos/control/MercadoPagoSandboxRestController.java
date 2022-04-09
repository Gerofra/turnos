package com.turnos.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.turnos.dto.NewPreferenceDTO;
import com.turnos.services.PreferenceService;

@RestController
@RequestMapping("/api")
public class MercadoPagoSandboxRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PreferenceService preferenceService;

	public MercadoPagoSandboxRestController(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPreference(@RequestBody NewPreferenceDTO preferenceDTO) throws MPException {
		return this.preferenceService.createPreference();
	}
}
