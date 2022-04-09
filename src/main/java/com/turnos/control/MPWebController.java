package com.turnos.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.mercadopago.exceptions.MPException;
import com.turnos.errors.ErrorService;
import com.turnos.services.PagoService;

@Controller
public class MPWebController {

	@Autowired
	PagoService pagoService;

	@GetMapping("/generic")
	public RedirectView success(HttpServletRequest request, @RequestParam("collection_id") Long collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String externalReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, RedirectAttributes attributes)
			throws MPException, ErrorService {

		pagoService.agregarPago("", collectionId);

		attributes.addFlashAttribute("genericResponse", true);
		attributes.addFlashAttribute("collection_id", collectionId);
		attributes.addFlashAttribute("collection_status", collectionStatus);
		attributes.addFlashAttribute("external_reference", externalReference);
		attributes.addFlashAttribute("payment_type", paymentType);
		attributes.addFlashAttribute("merchant_order_id", merchantOrderId);
		attributes.addFlashAttribute("preference_id", preferenceId);
		attributes.addFlashAttribute("site_id", siteId);
		attributes.addFlashAttribute("processing_mode", processingMode);
		attributes.addFlashAttribute("merchant_account_id", merchantAccountId);

		return new RedirectView("/mp");
	}
}
