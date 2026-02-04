package com.pauletchells.europa.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pauletchells.europa.domain.Game;
import com.pauletchells.europa.domain.GameConfiguration;
import com.pauletchells.europa.domain.Player;
import com.pauletchells.europa.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@PostMapping("/configure")
	public ResponseEntity<Game> configureGame(@RequestBody GameConfiguration configuration) {
		try {
			gameService.configureGame(configuration);
			return ResponseEntity.ok(gameService.getCurrentGame());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/configuration")
	public ResponseEntity<GameConfiguration> getConfiguration() {
		if (!gameService.isGameStarted()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(gameService.getCurrentConfiguration());
	}

	@GetMapping("/current-player")
	public ResponseEntity<Player> getCurrentPlayer() {
		Player player = gameService.getCurrentPlayer();
		if (player == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(player);
	}

	@GetMapping("/next-player")
	public ResponseEntity<Player> getNextPlayer() {
		Player player = gameService.getNextPlayer();
		if (player == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(player);
	}

	@PostMapping("/end-turn")
	public ResponseEntity<Player> endTurn() {
		if (!gameService.isGameStarted()) {
			return ResponseEntity.badRequest().build();
		}
		gameService.endTurn();
		return ResponseEntity.ok(gameService.getCurrentPlayer());
	}

	@PostMapping("/reset")
	public ResponseEntity<Void> resetGame() {
		gameService.resetGame();
		return ResponseEntity.ok().build();
	}
}
