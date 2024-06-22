/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.OAuth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.Genesis.SwiftSend.Registration.Token.JwtTokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rohan
 *
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler
		implements
			AuthenticationSuccessHandler {

	private final JwtTokenService jwtTokenService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String jwtToken = generateJwtToken(authentication);

		// Log authentication success
		log.info("Authentication succeeded for user: {}",
				authentication.getName());
		// Set response status and content type
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");

		// Write JSON response with token
		String jsonResponse = "{\"authenticated\": true, \"token\": \""
				+ jwtToken + "\"}";
		response.getWriter().write(jsonResponse);
	}

	private String generateJwtToken(Authentication authentication) {
		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2User oauth2User = ((OAuth2AuthenticationToken) authentication)
					.getPrincipal();
			return jwtTokenService.generateJwt(oauth2User);
		} else {
			throw new IllegalArgumentException(
					"Unsupported authentication: " + authentication);
		}
	}
}
