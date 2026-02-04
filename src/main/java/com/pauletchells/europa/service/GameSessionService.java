package com.pauletchells.europa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pauletchells.europa.domain.GameConfiguration;
import com.pauletchells.europa.persistence.GameSession;
import com.pauletchells.europa.persistence.GameSessionRepository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * GameSessionService manages game sessions persisted in the database.
 * Handles serialization/deserialization of game state to JSON.
 */
@Service
public class GameSessionService {
	private final GameSessionRepository gameSessionRepository;
	private final GameService gameService;
	private final ObjectMapper objectMapper;

	// In-memory cache of active games for this session
	private final Map<UUID, GameService> activeGames = new HashMap<>();

	public GameSessionService(GameSessionRepository gameSessionRepository, GameService gameService,
			ObjectMapper objectMapper) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameService = gameService;
		this.objectMapper = objectMapper;
	}

	public void configureGame(UUID sessionId, GameConfiguration config) {
		// Create game in memory
		gameService.configureGame(config);

		// Save game state to database
		GameSession session = gameSessionRepository.findBySessionId(sessionId)
				.orElseThrow(() -> new IllegalArgumentException("Session not found"));

		try {
			String gameStateJson = objectMapper.writeValueAsString(gameService.getCurrentGame());
			session.setGameStateJson(gameStateJson);
			gameSessionRepository.save(session);

			// Cache active game
			activeGames.put(sessionId, gameService);
		} catch (Exception e) {
			throw new RuntimeException("Failed to serialize game state", e);
		}
	}

	public GameService getGameService(UUID sessionId) {
		return activeGames.computeIfAbsent(sessionId, sid -> {
			GameSession session = gameSessionRepository.findBySessionId(sid)
					.orElseThrow(() -> new IllegalArgumentException("Session not found"));

			// Deserialize game state from database
			if (session.getGameStateJson() != null) {
				try {
					Object gameState = objectMapper.readValue(session.getGameStateJson(), Object.class);
					// NOTE: Full deserialization requires setting up GameService with loaded state
					// For now, this is a placeholder
					return gameService;
				} catch (Exception e) {
					throw new RuntimeException("Failed to deserialize game state", e);
				}
			}
			return new GameService();
		});
	}

	public void saveGameState(UUID sessionId) {
		GameSession session = gameSessionRepository.findBySessionId(sessionId)
				.orElseThrow(() -> new IllegalArgumentException("Session not found"));

		try {
			String gameStateJson = objectMapper.writeValueAsString(gameService.getCurrentGame());
			session.setGameStateJson(gameStateJson);
			gameSessionRepository.save(session);
		} catch (Exception e) {
			throw new RuntimeException("Failed to serialize game state", e);
		}
	}
}
