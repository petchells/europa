package com.pauletchells.europa.service;

import org.springframework.stereotype.Service;

import com.pauletchells.europa.domain.EndGameScore;
import com.pauletchells.europa.domain.PlayerProgress;

@Service
public class ScoringService {

	/**
	 * Calculate end-game fortune for a player.
	 * 
	 * Total Fortune = Coins from game + Star coins + Territory coins + Resource
	 * coins + Structure bonus
	 * 
	 * Coins earned depend on popularity level multiplier:
	 * - Popularity 0-3: 1 coin per achievement
	 * - Popularity 4-7: 2 coins per achievement
	 * - Popularity 8-11: 3 coins per achievement
	 * - Popularity 12-15: 4 coins per achievement
	 * - Popularity 16-18: 5 coins per achievement
	 * 
	 * @param progress Player's game progress
	 * @return EndGameScore with breakdown of fortune
	 */
	public EndGameScore calculateEndGameScore(PlayerProgress progress) {
		int coinsFromGame = progress.getCoins();

		// Determine popularity multiplier
		int popularityMultiplier = getPopularityMultiplier(progress.getPopularity());

		// Calculate coins from stars (1 star = 1 achievement point)
		int starCoins = progress.getStarCount() * popularityMultiplier;

		// Calculate coins from territories
		int territoryCoins = progress.getTerritoriesControlled() * popularityMultiplier;

		// Calculate coins from resources (every 2 resources = 1 point)
		int resourceCoins = (progress.getResourcesControlled() / 2) * popularityMultiplier;

		// Structure bonuses are fixed and don't depend on popularity
		int structureBonus = progress.getStructureBonuses();

		// Total fortune
		int totalFortune = coinsFromGame + starCoins + territoryCoins + resourceCoins + structureBonus;

		return new EndGameScore(
				starCoins + territoryCoins + resourceCoins, // coinsEarned
				starCoins,
				territoryCoins,
				resourceCoins,
				structureBonus,
				coinsFromGame,
				totalFortune);
	}

	/**
	 * Get the popularity-based multiplier for end-game scoring.
	 * 
	 * @param popularity Player's popularity level
	 * @return Coin multiplier (1-5)
	 */
	private int getPopularityMultiplier(int popularity) {
		if (popularity >= 16) {
			return 5;
		} else if (popularity >= 12) {
			return 4;
		} else if (popularity >= 8) {
			return 3;
		} else if (popularity >= 4) {
			return 2;
		} else {
			return 1;
		}
	}
}
