package com.pauletchells.europa.domain;

public record EncounterToken(
		int id,
		String description) {
	public static final int TOTAL_ENCOUNTER_TOKENS = 12;

	public EncounterToken {
		if (id < 1 || id > TOTAL_ENCOUNTER_TOKENS) {
			throw new IllegalArgumentException("Encounter token ID must be between 1 and " + TOTAL_ENCOUNTER_TOKENS);
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("Description cannot be null or empty");
		}
	}
}
