package com.pauletchells.europa.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record Game(
		GameConfiguration configuration,
		int currentPlayerIndex,
		Map<Integer, PlayerProgress> playerProgress,
		GameBoard gameBoard,
		boolean finished,
		Optional<Integer> winnerId) {
	public Game {
		if (configuration == null) {
			throw new IllegalArgumentException("Configuration cannot be null");
		}
		if (currentPlayerIndex < 0 || currentPlayerIndex >= configuration.players().size()) {
			throw new IllegalArgumentException("Invalid player index");
		}
		if (playerProgress == null) {
			throw new IllegalArgumentException("Player progress cannot be null");
		}
		if (gameBoard == null) {
			throw new IllegalArgumentException("Game board cannot be null");
		}
	}

	public Game(GameConfiguration configuration, int startingPlayerIndex) {
		this(configuration, startingPlayerIndex, initializePlayerProgress(configuration), new GameBoard(), false,
				Optional.empty());
	}

	private static Map<Integer, PlayerProgress> initializePlayerProgress(GameConfiguration configuration) {
		Map<Integer, PlayerProgress> progress = new HashMap<>();
		for (Player player : configuration.players()) {
			if (player.type() != PlayerType.NONE) {
				progress.put(player.position(), new PlayerProgress(player));
			}
		}
		return progress;
	}

	public Player getCurrentPlayer() {
		return configuration.players().get(currentPlayerIndex);
	}

	public Player getNextPlayer() {
		int nextIndex = getNextPlayerIndex();
		return configuration.players().get(nextIndex);
	}

	public int getNextPlayerIndex() {
		int nextIndex = currentPlayerIndex;
		do {
			nextIndex = (nextIndex + 1) % configuration.players().size();
		} while (configuration.players().get(nextIndex).type() == PlayerType.NONE);
		return nextIndex;
	}

	public PlayerProgress getCurrentPlayerProgress() {
		return playerProgress.get(getCurrentPlayer().position());
	}

	public Game advanceTurn() {
		int nextIndex = getNextPlayerIndex();
		return new Game(configuration, nextIndex, playerProgress, gameBoard, finished, winnerId);
	}

	public Game updatePlayerProgress(Player player, PlayerProgress progress) {
		Map<Integer, PlayerProgress> updated = new HashMap<>(playerProgress);
		updated.put(player.position(), progress);

		// Check if this player has won
		if (progress.hasWon()) {
			return new Game(configuration, currentPlayerIndex, updated, gameBoard, true,
					Optional.of(player.position()));
		}

		return new Game(configuration, currentPlayerIndex, updated, gameBoard, finished, winnerId);
	}

	public boolean hasWinner() {
		return finished && winnerId.isPresent();
	}
}
