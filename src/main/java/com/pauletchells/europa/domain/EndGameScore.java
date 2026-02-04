package com.pauletchells.europa.domain;

public record EndGameScore(
		int coinsEarned,
		int starCoins,
		int territoryCoins,
		int resourceCoins,
		int structureBonus,
		int coinsFromGame,
		int totalFortune) {
	public EndGameScore {
		if (coinsEarned < 0 || starCoins < 0 || territoryCoins < 0 ||
				resourceCoins < 0 || structureBonus < 0 || coinsFromGame < 0 || totalFortune < 0) {
			throw new IllegalArgumentException("All coin values must be non-negative");
		}
	}
}
