package com.pauletchells.europa.domain;

import java.util.HashSet;
import java.util.Set;

public class PlayerProgress {
	private final Player player;
	private final PlayerComponents components;
	private final Set<Star> earnedStars;

	private int upgrades; // 0-6
	private int mechs; // 0-4
	private int structures; // 0-4
	private int recruits; // 0-4
	private int workers; // 0-8
	private int combatWins; // 0-2
	private int popularity; // 0+
	private int power; // 0+
	private boolean objectiveCompleted; // 0-1

	private int coins; // Currency earned during game
	private int territoriesControlled; // Number of territories controlled
	private int resourcesControlled; // Number of resources controlled
	private int structureBonuses; // Bonus coins from structure placement

	public PlayerProgress(Player player) {
		this.player = player;
		this.components = new PlayerComponents();
		this.earnedStars = new HashSet<>();
		this.upgrades = 0;
		this.mechs = 0;
		this.structures = 0;
		this.recruits = 0;
		this.workers = 0;
		this.combatWins = 0;
		this.popularity = 0;
		this.power = 0;
		this.objectiveCompleted = false;
		this.coins = 0;
		this.territoriesControlled = 0;
		this.resourcesControlled = 0;
		this.structureBonuses = 0;
	}

	public Player getPlayer() {
		return player;
	}

	public PlayerComponents getComponents() {
		return components;
	}

	public Set<Star> getEarnedStars() {
		return new HashSet<>(earnedStars);
	}

	public int getStarCount() {
		return earnedStars.size();
	}

	public void addUpgrade() {
		if (upgrades < 6) {
			upgrades++;
			checkUpgradesStar();
		}
	}

	public void addMech() {
		if (mechs < 4) {
			mechs++;
			checkMechsStar();
		}
	}

	public void addStructure() {
		if (structures < 4) {
			structures++;
			checkStructuresStar();
		}
	}

	public void addRecruit() {
		if (recruits < 4) {
			recruits++;
			checkRecruitsStar();
		}
	}

	public void addWorker() {
		if (workers < 8) {
			workers++;
			checkWorkersStar();
		}
	}

	public void addCombatWin() {
		if (combatWins < 2) {
			combatWins++;
			checkCombatStar();
		}
	}

	public void completeObjective() {
		if (!objectiveCompleted) {
			objectiveCompleted = true;
			checkObjectiveStar();
		}
	}

	public void setPopularity(int popularity) {
		this.popularity = Math.max(0, popularity);
		checkPopularityStar();
	}

	public void setPower(int power) {
		this.power = Math.max(0, power);
		checkPowerStar();
	}

	public void addCoins(int amount) {
		this.coins += Math.max(0, amount);
	}

	public void setCoins(int amount) {
		this.coins = Math.max(0, amount);
	}

	public void setTerritoriesControlled(int count) {
		this.territoriesControlled = Math.max(0, count);
	}

	public void setResourcesControlled(int count) {
		this.resourcesControlled = Math.max(0, count);
	}

	public void addStructureBonus(int bonus) {
		this.structureBonuses += Math.max(0, bonus);
	}

	public void setStructureBonus(int bonus) {
		this.structureBonuses = Math.max(0, bonus);
	}

	private void checkUpgradesStar() {
		if (upgrades == 6) {
			earnedStars.add(Star.ALL_UPGRADES);
		}
	}

	private void checkMechsStar() {
		if (mechs == 4) {
			earnedStars.add(Star.ALL_MECHS);
		}
	}

	private void checkStructuresStar() {
		if (structures == 4) {
			earnedStars.add(Star.ALL_STRUCTURES);
		}
	}

	private void checkRecruitsStar() {
		if (recruits == 4) {
			earnedStars.add(Star.ALL_RECRUITS);
		}
	}

	private void checkWorkersStar() {
		if (workers == 8) {
			earnedStars.add(Star.ALL_WORKERS);
		}
	}

	private void checkObjectiveStar() {
		if (objectiveCompleted) {
			earnedStars.add(Star.OBJECTIVE_CARD);
		}
	}

	private void checkCombatStar() {
		if (combatWins == 1) {
			earnedStars.add(Star.FIRST_COMBAT_WIN);
		} else if (combatWins == 2) {
			earnedStars.add(Star.SECOND_COMBAT_WIN);
		}
	}

	private void checkPopularityStar() {
		if (popularity >= 18) {
			earnedStars.add(Star.POPULARITY_18);
		} else {
			earnedStars.remove(Star.POPULARITY_18);
		}
	}

	private void checkPowerStar() {
		if (power >= 16) {
			earnedStars.add(Star.POWER_16);
		} else {
			earnedStars.remove(Star.POWER_16);
		}
	}

	public boolean hasWon() {
		return earnedStars.size() >= 6;
	}

	// Getters for progress tracking
	public int getUpgrades() {
		return upgrades;
	}

	public int getMechs() {
		return mechs;
	}

	public int getStructures() {
		return structures;
	}

	public int getRecruits() {
		return recruits;
	}

	public int getWorkers() {
		return workers;
	}

	public int getCombatWins() {
		return combatWins;
	}

	public int getPopularity() {
		return popularity;
	}

	public int getPower() {
		return power;
	}

	public boolean isObjectiveCompleted() {
		return objectiveCompleted;
	}

	public int getCoins() {
		return coins;
	}

	public int getTerritoriesControlled() {
		return territoriesControlled;
	}

	public int getResourcesControlled() {
		return resourcesControlled;
	}

	public int getStructureBonuses() {
		return structureBonuses;
	}
}
