package com.gg.demo.controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	private RelyingPartyRegistrationRepository relyingParties;
	
	@GetMapping("/")
	public ResponseEntity<?> getResponse(){
		
		Saml2Authentication auth = (Saml2Authentication) SecurityContextHolder.getContext().getAuthentication();
		HashMap<String, Object> data = new HashMap<>();
		data.put("username", auth.getName());
		
		return ResponseEntity.ok(data);	
	}	
	
	@GetMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String registrationId = "wso2";
		String registration = relyingParties.findByRegistrationId(registrationId).getIdpWebSsoUrl();
		response.sendRedirect("/saml2/authenticate/"+registrationId);
		
	}
	
}
