package com.pauletchells.europa.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * FactionMat represents a player's faction-specific board during the game.
 * It tracks stars, mechs, coins, and the faction's special ability.
 */
public class FactionMat {
	private final Faction faction;
	private final List<Integer> starTokens; // Stars placed on faction mat (max 6)
	private final List<Integer> mechTokens; // Mechs available on faction mat (max 4)
	private int coins; // Coins on faction mat
	private final String factionAbility; // Description of the faction's special ability

	private FactionMat(Faction faction, String ability) {
		this.faction = faction;
		this.starTokens = new ArrayList<>();
		this.mechTokens = new ArrayList<>();
		this.coins = 0;
		this.factionAbility = ability;

		// Initialize 6 stars and 4 mechs on the mat
		for (int i = 0; i < 6; i++) {
			starTokens.add(i);
		}
		for (int i = 0; i < 4; i++) {
			mechTokens.add(i);
		}
	}

	public static FactionMat create(Faction faction) {
		return switch (faction) {
			case NORDIC -> new FactionMat(faction, "SWIM: Your workers may move across rivers.");
			case SAXONY -> new FactionMat(faction,
					"DOMINATE: No limit to stars from objectives/combat; complete both objectives.");
			case POLANIA -> new FactionMat(faction, "MEANDER: Pick up to 2 options per encounter card.");
			case CRIMEA -> new FactionMat(faction, "COERCION: Spend 1 combat card as any 1 resource once per turn.");
			case RUSVIET ->
				new FactionMat(faction, "RELENTLESS: Choose the same Player Mat section on subsequent turns.");
		};
	}

	public Faction getFaction() {
		return faction;
	}

	public String getFactionAbility() {
		return factionAbility;
	}

	public int getStarCount() {
		return starTokens.size();
	}

	public int getMechCount() {
		return mechTokens.size();
	}

	public int getCoins() {
		return coins;
	}

	public void addCoins(int amount) {
		this.coins += amount;
	}

	public void spendCoins(int amount) {
		this.coins = Math.max(0, this.coins - amount);
	}

	public boolean canPlaceStar() {
		return starTokens.size() > 0;
	}

	public void placeStar() {
		if (!starTokens.isEmpty()) {
			starTokens.remove(starTokens.size() - 1);
		}
	}

	public boolean canPlaceMech() {
		return mechTokens.size() > 0;
	}

	public void removeMech() {
		// Mechs are deployed to the board; this removes from mat's available pool
		if (!mechTokens.isEmpty()) {
			mechTokens.remove(mechTokens.size() - 1);
		}
	}

	public void returnMech() {
		// Mechs can be returned if retreated
		if (mechTokens.size() < 4) {
			mechTokens.add(mechTokens.size());
		}
	}

	@Override
	public String toString() {
		return "FactionMat{" +
				"faction=" + faction +
				", stars=" + starTokens.size() +
				", mechs=" + mechTokens.size() +
				", coins=" + coins +
				'}';
	}
}
