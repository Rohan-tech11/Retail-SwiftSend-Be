/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
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
		request.setAttribute("startTime", Instant.now());

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
		Instant startTime = (Instant) request.getAttribute("startTime");
		Instant endTime = Instant.now();
		long durationMs = Duration.between(startTime, endTime).toMillis();

		if (ex != null) {
			logger.error(
					"There was an error from the Response of RequestId: {}. Error: {}",
					requestId, ex.getMessage(), ex);
		} else {
			logger.info(
					"Response from RequestId: {}. Response status code: {}. Time taken: {} ms",
					requestId, status, durationMs);
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