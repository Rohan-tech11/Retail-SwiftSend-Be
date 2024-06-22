/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.OAuth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rohan
 *
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler
		implements
			AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		// Log the authentication failure
		log.error("Authentication failed: {}", exception.getMessage());

		// Set response status and content type
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		// Write response body
		String jsonResponse = "{\"error\": \"Authentication failed: "
				+ exception.getMessage() + "\"}";
		response.getWriter().write(jsonResponse);
	}
}