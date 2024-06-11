/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * @author rohan
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(LoggingInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		String requestUrl = request.getMethod() + " " + request.getRequestURI();
		String queryString = request.getQueryString();
		if (queryString != null) {
			requestUrl += "?" + queryString;
		}
		UUID requestId = UUID.randomUUID();
		request.setAttribute("requestId", requestId.toString());

		logger.info("RequestId: {} RequestUrl: {}", requestId, requestUrl);
		logger.info("Request Headers: {}", getHeadersInfo(request));

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		UUID requestId = UUID
				.fromString((String) request.getAttribute("requestId"));
		int status = response.getStatus();

		if (ex != null) {
			logger.error(
					"There was an error from the Response of RequestId: {}. Error: {}",
					requestId, ex.getMessage(), ex);
		} else {
			logger.info("Response from RequestId: {}. Response status code: {}",
					requestId, status);
		}
	}

	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> headersInfo = Collections.emptyMap();
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
			headersInfo = Collections.singletonMap("Authorization",
					authorizationHeader);
		} else {
			headersInfo = Collections.singletonMap("Authorization", "null");
		}
		return headersInfo;
	}

}
