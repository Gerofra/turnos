package com.turnos.config;

import com.mercadopago.MercadoPago;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import com.turnos.entities.Pago;


@RestController
public class Pagos {
		
	
	@GetMapping("/mp")
	public Pago mp() throws MPException {
		MercadoPago.SDK.setAccessToken("TEST-7906613297114815-022012-784cb5d4681cda744ee7525de740d34f-250383898");
		
		Preference preference = new Preference();
		// Crea un ítem en la preferencia
		Item item = new Item();
		item.setId("1")
		.setTitle("Mi producto")
		    .setQuantity(1)
		    .setUnitPrice((float) 75.56);
		preference.appendItem(item);
		preference.save();
		
		return new Pago("1", "Mi producto");
	}


    
	
	
}
