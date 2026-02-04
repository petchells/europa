package com.pauletchells.europa.domain;

import java.util.List;
import java.util.stream.Collectors;

public record GameConfiguration(
		List<Player> players) {
	public GameConfiguration {
		if (players == null || players.isEmpty()) {
			throw new IllegalArgumentException("At least one player must be configured");
		}
		if (players.size() > 5) {
			throw new IllegalArgumentException("Maximum 5 players allowed");
		}

		long activePlayers = players.stream()
				.filter(Player::isActive)
				.count();
		if (activePlayers < 1 || activePlayers > 5) {
			throw new IllegalArgumentException("Game must have between 1 and 5 active players");
		}
	}

	public int getActivePlayerCount() {
		return (int) players.stream()
				.filter(Player::isActive)
				.count();
	}

	public List<Player> getActivePlayers() {
		return players.stream()
				.filter(Player::isActive)
				.collect(Collectors.toList());
	}

	public List<Player> getHumanPlayers() {
		return players.stream()
				.filter(p -> p.type() == PlayerType.HUMAN)
				.collect(Collectors.toList());
	}

	public List<Player> getCpuPlayers() {
		return players.stream()
				.filter(p -> p.type() == PlayerType.CPU)
				.collect(Collectors.toList());
	}
}
