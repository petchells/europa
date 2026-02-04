package com.pauletchells.europa.domain;

public enum Faction {
	NORDIC("Nordic", "The viking traders"),
	SAXONY("Saxony", "The industrial machine"),
	POLANIA("Polania", "The agricultural heartland"),
	CRIMEA("Crimea", "The nomadic warriors"),
	RUSVIET("Rusviet", "The red bear of communism");

	private final String displayName;
	private final String description;

	Faction(String displayName, String description) {
		this.displayName = displayName;
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}
}
