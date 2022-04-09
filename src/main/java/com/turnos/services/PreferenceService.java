package com.turnos.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mercadopago.MercadoPago;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import com.turnos.dto.NewPreferenceDTO;

@Service
public class PreferenceService {
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public ResponseEntity<?> create(NewPreferenceDTO preferenceDTO) throws MPException {

		/*
		 * if (StringUtils.isEmpty(preferenceDTO.getAccessToken())) { return
		 * ResponseEntity.badRequest().body("Access token is mandatory"); }
		 */
		if (preferenceDTO.getItems().isEmpty()) {
			return ResponseEntity.badRequest().body("Items empty");
		}

		MercadoPago.SDK.setAccessToken("TEST-7906613297114815-022012-784cb5d4681cda744ee7525de740d34f-250383898");
		String notificationUrl = "/generic";

		Preference p = new Preference();
		p.setBackUrls(
				new BackUrls().setSuccess(notificationUrl).setPending(notificationUrl).setFailure(notificationUrl));
		p.setItems(preferenceDTO.getItems().stream().map(i -> {
			Item item = new Item();
			item.setId(i.getId());
			item.setUnitPrice(i.getPrice());
			item.setTitle(i.getName());
			item.setQuantity(i.getQuantity());

			return item;
		}).collect(Collectors.toCollection(ArrayList::new)));

		p.save();

		if (StringUtils.isEmpty(p.getId())) {
			return ResponseEntity.status(404).body(
					Collections.singletonMap("Message", "Preference was not created. Check if Access Token is valid"));
		}
		return ResponseEntity.ok(gson.toJson(p));
	}

	public ResponseEntity<?> createPreference() throws MPException {

		MercadoPago.SDK.setAccessToken("TEST-7906613297114815-022012-784cb5d4681cda744ee7525de740d34f-250383898");
		String notificationUrl = "http://localhost:8080/generic";

		Preference p = new Preference();
		p.setBackUrls(
				new BackUrls().setSuccess(notificationUrl).setPending(notificationUrl).setFailure(notificationUrl));

		p.setAutoReturn(Preference.AutoReturn.approved);
		p.setNotificationUrl("https://gerofra.requestcatcher.com/");

		Item item = new Item();
		item.setId("1").setTitle("Producto facherisimo").setDescription("Re piola piolita").setQuantity(1)
				.setUnitPrice((float) 75.56);

		ArrayList<Item> items = new ArrayList<>();
		items.add(item);

		p.setItems(items);
		p.save();

		if (StringUtils.isEmpty(p.getId())) {
			return ResponseEntity.status(404).body(
					Collections.singletonMap("Message", "Preference was not created. Check if Access Token is valid"));
		}

		return ResponseEntity.ok(gson.toJson(p));
	}
}
