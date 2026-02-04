package com.pauletchells.europa.domain;

public enum Resource {
	WOOD("Wood"),
	OIL("Oil"),
	FOOD("Food"),
	METAL("Metal");

	private final String displayName;

	Resource(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
