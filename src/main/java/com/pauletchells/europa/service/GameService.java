package com.pauletchells.europa.service;

import org.springframework.stereotype.Service;

import com.pauletchells.europa.domain.Faction;
import com.pauletchells.europa.domain.Game;
import com.pauletchells.europa.domain.GameBoard;
import com.pauletchells.europa.domain.GameConfiguration;
import com.pauletchells.europa.domain.Player;
import com.pauletchells.europa.domain.PlayerComponents;
import com.pauletchells.europa.domain.PlayerProgress;
import com.pauletchells.europa.domain.UnitType;

@Service
public class GameService {

	private Game currentGame;

	public void configureGame(GameConfiguration configuration) {
		// Find first active player to start
		int startingPlayerIndex = 0;
		for (int i = 0; i < configuration.players().size(); i++) {
			if (configuration.players().get(i).type().ordinal() < 2) { // HUMAN or CPU
				startingPlayerIndex = i;
				break;
			}
		}
		this.currentGame = new Game(configuration, startingPlayerIndex);
	}

	public GameConfiguration getCurrentConfiguration() {
		return currentGame != null ? currentGame.configuration() : null;
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public GameBoard getGameBoard() {
		if (currentGame == null) {
			return null;
		}
		return currentGame.gameBoard();
	}

	public com.pauletchells.europa.domain.HomeBase[] getHomeBases() {
		GameBoard board = getGameBoard();
		if (board == null)
			return null;
		return board.getHomeBases();
	}

	public com.pauletchells.europa.domain.HomeBase getHomeBase(int id) {
		GameBoard board = getGameBoard();
		if (board == null)
			return null;
		try {
			return board.getHomeBase(id);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public boolean setHomeBaseOwner(int id, com.pauletchells.europa.domain.Faction faction) {
		com.pauletchells.europa.domain.HomeBase hb = getHomeBase(id);
		if (hb == null)
			return false;
		hb.setOwner(faction);
		return true;
	}

	public Player getCurrentPlayer() {
		if (currentGame == null) {
			return null;
		}
		return currentGame.getCurrentPlayer();
	}

	public Player getNextPlayer() {
		if (currentGame == null) {
			return null;
		}
		return currentGame.getNextPlayer();
	}

	public void endTurn() {
		if (currentGame != null) {
			currentGame = currentGame.advanceTurn();
		}
	}

	public PlayerProgress getPlayerProgress(int playerPosition) {
		if (currentGame == null) {
			return null;
		}
		return currentGame.playerProgress().get(playerPosition);
	}

	public PlayerComponents getPlayerComponents(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		return progress.getComponents();
	}

	public PlayerProgress addUpgrade(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.addUpgrade();
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress addMech(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.addMech();
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress addStructure(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.addStructure();
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress addRecruit(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.addRecruit();
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress addWorker(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.addWorker();
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress addCombatWin(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.addCombatWin();
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress completeObjective(int playerPosition) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.completeObjective();
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress setPopularity(int playerPosition, int value) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.setPopularity(value);
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public PlayerProgress setPower(int playerPosition, int value) {
		PlayerProgress progress = getPlayerProgress(playerPosition);
		if (progress == null) {
			return null;
		}
		progress.setPower(value);
		updateGameWithProgress(playerPosition, progress);
		return progress;
	}

	public boolean placeUnitOnTerritory(int playerPosition, int territoryIndex, UnitType type) {
		Player player = currentGame == null ? null
				: currentGame.configuration().players().stream()
						.filter(p -> p.position() == playerPosition).findFirst().orElse(null);
		if (player == null)
			return false;
		Faction faction = player.faction();
		PlayerComponents components = getPlayerComponents(playerPosition);
		if (components == null)
			return false;
		// Check token availability
		switch (type) {
			case WORKER:
				if (components.getWorkerTokensRemaining() <= 0)
					return false;
				components.placeWorkerToken();
				break;
			case MECH:
				if (components.getMechTokensRemaining() <= 0)
					return false;
				components.placeMechToken();
				break;
			case CHARACTER:
				if (components.isCharacterMiniaturePlaced())
					return false;
				components.placeCharacterMiniature();
				break;
		}
		GameBoard board = getGameBoard();
		if (board == null)
			return false;
		try {
			GameBoard.Territory territory = board.getTerritory(territoryIndex);
			territory.placeUnit(faction, type);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public boolean removeUnitFromTerritory(int playerPosition, int territoryIndex, UnitType type) {
		Player player = currentGame == null ? null
				: currentGame.configuration().players().stream()
						.filter(p -> p.position() == playerPosition).findFirst().orElse(null);
		if (player == null)
			return false;
		Faction faction = player.faction();
		GameBoard board = getGameBoard();
		if (board == null)
			return false;
		try {
			GameBoard.Territory territory = board.getTerritory(territoryIndex);
			territory.removeUnit(faction, type);
			// NOTE: tokens are NOT returned to the player's pool here to keep state simple.
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public boolean spendResourcesFromTerritory(int playerPosition, int territoryIndex, int count) {
		Player player = currentGame == null ? null
				: currentGame.configuration().players().stream()
						.filter(p -> p.position() == playerPosition).findFirst().orElse(null);
		if (player == null)
			return false;
		Faction faction = player.faction();
		GameBoard board = getGameBoard();
		if (board == null)
			return false;
		try {
			GameBoard.Territory territory = board.getTerritory(territoryIndex);
			Faction controller = territory.getControllingFaction();
			if (controller == null || controller != faction)
				return false;
			int available = territory.getResourceCount();
			if (available < count)
				return false;
			territory.setResourceCount(available - count);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private void updateGameWithProgress(int playerPosition, PlayerProgress progress) {
		if (currentGame != null) {
			// Find the player with this position
			Player player = currentGame.configuration().players().stream()
					.filter(p -> p.position() == playerPosition)
					.findFirst()
					.orElse(null);

			if (player != null) {
				currentGame = currentGame.updatePlayerProgress(player, progress);
			}
		}
	}

	public void updatePlayerProgress(int playerPosition, PlayerProgress progress) {
		updateGameWithProgress(playerPosition, progress);
	}

	public boolean isGameStarted() {
		return currentGame != null;
	}

	public boolean hasGameEnded() {
		return currentGame != null && currentGame.finished();
	}

	public Player getWinner() {
		if (currentGame != null && currentGame.winnerId().isPresent()) {
			int winnerId = currentGame.winnerId().get();
			return currentGame.configuration().players().stream()
					.filter(p -> p.position() == winnerId)
					.findFirst()
					.orElse(null);
		}
		return null;
	}

	public void resetGame() {
		this.currentGame = null;
	}
}
