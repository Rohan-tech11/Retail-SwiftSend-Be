/**
 * created by @Rohan
 */
package com.Genesis.SwiftSend.Client;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Genesis.SwiftSend.ResponseHandler.ResponseHandler;
import com.Genesis.SwiftSend.UserOrder.IOrdersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rohan
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
@CrossOrigin("*")
@Slf4j
public class ClientController {

	private final ClientService clientService;

	private final IOrdersService ordersService;

	// to do : do research on making authenticaton object and loggedin client email
	// in global scope
	@PostMapping("/addService")
	public ResponseEntity<Object> addService(@RequestBody ClientServiceRequest serviceRequest,
			Authentication authentication) {

		Object principal = authentication.getPrincipal();
		log.info("Principal type after jwt generated: " + principal.getClass().getName());

		// Check if the principal is a Jwt
		if (authentication.getPrincipal() instanceof Jwt) {
			// Extract information from the Jwt
			String userEmail = ((Jwt) authentication.getPrincipal()).getClaim("email");

			// Now you can use userEmail in your logic or pass it to your service
			return clientService.addService(serviceRequest, userEmail);
		} else {
			// Handle the case where the principal is not a Jwt
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid authentication principal type.");
		}
	}

	@GetMapping("/isAdminApproved")
	public ResponseEntity<Object> isAdmminApproved(Authentication authentication) {

		String clientEmail = ((Jwt) authentication.getPrincipal()).getClaim("email");
		boolean isAdminApproved = clientService.isAdminApproved(clientEmail);
		if (isAdminApproved) {
			return ResponseHandler.responseBuilder("Admin Approved this Client Account ", HttpStatus.ACCEPTED,
					isAdminApproved);
		} else {
			return ResponseHandler.responseBuilder("No Admin Approval for  this Client Account ",
					HttpStatus.NOT_ACCEPTABLE, isAdminApproved);
		}

	}

	@GetMapping("/fetchServices")
	public ResponseEntity<Object> fetchServices(Authentication authentication) {

		String clientEmail = ((Jwt) authentication.getPrincipal()).getClaim("email");
		List<Map<String, Object>> clientServicesEmail = clientService.fetchClientServices(clientEmail);
		if (!clientServicesEmail.isEmpty()) {
			return ResponseHandler.responseBuilder("Fetched the Client services ", HttpStatus.OK, clientServicesEmail);
		} else {
			return ResponseHandler.responseBuilder("No Services available for this client ", HttpStatus.OK);

		}
	}

	@GetMapping("/getAllOrders")
	public ResponseEntity<Object> getUserOrder(Authentication authentication) {

		String clientEmail = ((Jwt) authentication.getPrincipal()).getClaim("email");
		Optional<Client> client = clientService.findByEmail(clientEmail);
		List<Map<String, Object>> clientOrders = ordersService.getOrdersByClient(client.get());
		return ResponseHandler.responseBuilder("Fetched the Client  orders ", HttpStatus.OK, clientOrders);

	}

	@PostMapping("/createQuotation/{orderId}")
	public ResponseEntity<Object> createClientQuotation(@PathVariable Long orderId,
			@RequestBody QuotationRequest quotationRequest) {

		clientService.createQuotation(orderId, quotationRequest);
		return ResponseHandler.responseBuilder("saved the client quotation ", HttpStatus.OK);
	}
}
