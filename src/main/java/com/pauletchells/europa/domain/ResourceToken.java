package com.pauletchells.europa.domain;

public record ResourceToken(
		Resource resource,
		int quantity) {
	public ResourceToken {
		if (resource == null) {
			throw new IllegalArgumentException("Resource cannot be null");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity must be non-negative");
		}
	}
}
