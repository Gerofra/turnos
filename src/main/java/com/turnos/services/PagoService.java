package com.turnos.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turnos.entities.Pago;
import com.turnos.errors.ErrorService;
import com.turnos.repos.PagoRepo;

@Service
public class PagoService {

	@Autowired
	PagoRepo pagoRepo;

	@Transactional
	public void agregarPago(String mail, Long idPago) throws ErrorService {

		Pago pago = new Pago();
		pago.setIdPago(idPago);
		pago.setMail(mail);

		pagoRepo.save(pago);
	}
}
