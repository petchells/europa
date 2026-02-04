package com.pauletchells.europa.domain;

public record StructureBonusTile(
		int id,
		String location,
		int coinBonus) {
	public static final int TOTAL_STRUCTURE_BONUS_TILES = 6;

	public StructureBonusTile {
		if (id < 1 || id > TOTAL_STRUCTURE_BONUS_TILES) {
			throw new IllegalArgumentException(
					"Structure bonus tile ID must be between 1 and " + TOTAL_STRUCTURE_BONUS_TILES);
		}
		if (location == null || location.isBlank()) {
			throw new IllegalArgumentException("Location cannot be null or empty");
		}
		if (coinBonus < 0) {
			throw new IllegalArgumentException("Coin bonus must be non-negative");
		}
	}
}
