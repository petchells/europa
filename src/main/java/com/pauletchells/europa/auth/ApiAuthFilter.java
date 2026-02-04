package com.pauletchells.europa.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * ApiAuthFilter validates simple API key authorization.
 * Extracts the API key from the Authorization header (format: "Bearer <key>")
 * and stores it in a thread-local context for use by controllers.
 */
public class ApiAuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String apiKey = authHeader.substring(7);
			ApiKeyContext.setApiKey(apiKey);
		}

		try {
			filterChain.doFilter(request, response);
		} finally {
			ApiKeyContext.clear();
		}
	}
}
