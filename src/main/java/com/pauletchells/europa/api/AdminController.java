package com.pauletchells.europa.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pauletchells.europa.domain.GameConfiguration;
import com.pauletchells.europa.persistence.GameSession;
import com.pauletchells.europa.persistence.GameSessionRepository;
import com.pauletchells.europa.service.GameSessionService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private final GameSessionRepository gameSessionRepository;
	private final GameSessionService gameSessionService;

	public AdminController(GameSessionRepository gameSessionRepository, GameSessionService gameSessionService) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionService = gameSessionService;
	}

	@PostMapping("/game")
	public ResponseEntity<?> createGame(@RequestHeader("Authorization") String authHeader) {
		// Admin endpoint requires admin key in Authorization header
		if (!authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Invalid authorization header");
		}
		String adminKey = authHeader.substring(7);

		// Create new game session
		GameSession session = new GameSession(adminKey);
		GameSession saved = gameSessionRepository.save(session);

		Map<String, Object> response = new HashMap<>();
		response.put("sessionId", saved.getSessionId());
		response.put("adminKey", saved.getAdminKey());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/game/{sessionId}/configure")
	public ResponseEntity<?> configureGame(
			@PathVariable UUID sessionId,
			@RequestHeader("Authorization") String authHeader,
			@RequestBody GameConfiguration config) {
		// Validate admin key
		if (!authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Invalid authorization header");
		}
		String adminKey = authHeader.substring(7);

		GameSession session = gameSessionRepository.findBySessionId(sessionId)
				.orElse(null);
		if (session == null) {
			return ResponseEntity.notFound().build();
		}

		if (!session.validateAdminKey(adminKey)) {
			return ResponseEntity.status(401).body("Unauthorized: invalid admin key");
		}

		// Configure the game in memory
		gameSessionService.configureGame(sessionId, config);

		return ResponseEntity.ok("Game configured");
	}

	@PostMapping("/game/{sessionId}/player/{playerPosition}/key")
	public ResponseEntity<?> generatePlayerKey(
			@PathVariable UUID sessionId,
			@PathVariable int playerPosition,
			@RequestHeader("Authorization") String authHeader) {
		// Validate admin key
		if (!authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Invalid authorization header");
		}
		String adminKey = authHeader.substring(7);

		GameSession session = gameSessionRepository.findBySessionId(sessionId)
				.orElse(null);
		if (session == null) {
			return ResponseEntity.notFound().build();
		}

		if (!session.validateAdminKey(adminKey)) {
			return ResponseEntity.status(401).body("Unauthorized: invalid admin key");
		}

		// Generate player API key
		String playerKey = UUID.randomUUID().toString();
		session.addPlayerApiKey(playerPosition, playerKey);
		gameSessionRepository.save(session);

		Map<String, Object> response = new HashMap<>();
		response.put("playerPosition", playerPosition);
		response.put("apiKey", playerKey);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/game/{sessionId}")
	public ResponseEntity<?> getGameInfo(
			@PathVariable UUID sessionId,
			@RequestHeader("Authorization") String authHeader) {
		// Validate admin key
		if (!authHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().body("Invalid authorization header");
		}
		String adminKey = authHeader.substring(7);

		GameSession session = gameSessionRepository.findBySessionId(sessionId)
				.orElse(null);
		if (session == null) {
			return ResponseEntity.notFound().build();
		}

		if (!session.validateAdminKey(adminKey)) {
			return ResponseEntity.status(401).body("Unauthorized: invalid admin key");
		}

		Map<String, Object> info = new HashMap<>();
		info.put("sessionId", session.getSessionId());
		info.put("createdAt", session.getCreatedAt());
		info.put("updatedAt", session.getUpdatedAt());
		info.put("finished", session.isFinished());
		info.put("playerKeys", session.getPlayerApiKeys().keySet());

		return ResponseEntity.ok(info);
	}
}
