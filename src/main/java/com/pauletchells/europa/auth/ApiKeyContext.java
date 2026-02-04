package com.pauletchells.europa.auth;

/**
 * Thread-local context to store the current API key from the Authorization
 * header.
 * Used by controllers to validate requests without passing the key through
 * method signatures.
 */
public class ApiKeyContext {
	private static final ThreadLocal<String> apiKey = new ThreadLocal<>();

	public static void setApiKey(String key) {
		apiKey.set(key);
	}

	public static String getApiKey() {
		return apiKey.get();
	}

	public static void clear() {
		apiKey.remove();
	}
}
