package com.pauletchells.europa.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PlayerMat represents the action matrix a player uses to choose actions each
 * turn.
 * Each player mat has 4 sections with top-row and bottom-row actions.
 * The order of actions varies by faction (randomized each game).
 */
public class PlayerMat {
	private final List<String> actionRows; // 4 action sections (top & bottom pairs)

	private PlayerMat(List<String> actionRows) {
		this.actionRows = new ArrayList<>(actionRows);
	}

	/**
	 * Create a PlayerMat with the action order for a specific faction.
	 * Each faction has a unique order of the 4 action sections.
	 */
	public static PlayerMat create(Faction faction) {
		return switch (faction) {
			case NORDIC -> new PlayerMat(
					List.of("Trade & Gain", "Move & Gain", "Produce & Gain", "Enlist & Bolster"));
			case SAXONY -> new PlayerMat(
					List.of("Move & Produce", "Move & Produce", "Upgrade & Move", "Produce & Bolster"));
			case POLANIA -> new PlayerMat(
					List.of("Move & Produce", "Move & Bolster", "Trade & Produce", "Build & Bolster"));
			case CRIMEA -> new PlayerMat(
					List.of("Move & Trade", "Move & Trade", "Move & Produce", "Move & Bolster"));
			case RUSVIET -> new PlayerMat(
					List.of("Move & Bolster", "Move & Bolster", "Trade & Trade", "Enlist & Bolster"));
		};
	}

	public List<String> getActionRows() {
		return Collections.unmodifiableList(actionRows);
	}

	@Override
	public String toString() {
		return "PlayerMat{" +
				"actions=" + actionRows +
				'}';
	}
}
