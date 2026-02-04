package com.pauletchells.europa.domain;

public enum Star {
	ALL_UPGRADES("Complete all 6 upgrades"),
	ALL_MECHS("Deploy all 4 mechs"),
	ALL_STRUCTURES("Build all 4 structures"),
	ALL_RECRUITS("Enlist all 4 recruits"),
	ALL_WORKERS("Have all 8 workers on the board"),
	OBJECTIVE_CARD("Reveal 1 completed objective card"),
	FIRST_COMBAT_WIN("Win combat (1st victory)"),
	SECOND_COMBAT_WIN("Win combat (2nd victory)"),
	POPULARITY_18("Have 18 popularity"),
	POWER_16("Have 16 power");

	private final String description;

	Star(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
