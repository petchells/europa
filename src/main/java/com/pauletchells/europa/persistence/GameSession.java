package com.pauletchells.europa.persistence;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * GameSession represents a persistent game stored in the database.
 * Each session has:
 * - A unique session ID
 * - Game state (serialized as JSON)
 * - Player API keys for authorization
 * - Admin API key to manage the session
 */
@Entity
@Table(name = "game_sessions")
public class GameSession {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID sessionId;

	@Column(name = "admin_key", nullable = false, unique = true)
	private String adminKey;

	@Column(name = "game_state_json", columnDefinition = "TEXT")
	private String gameStateJson; // Serialized game state

	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@Column(name = "finished")
	private boolean finished = false;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "player_api_keys", joinColumns = @JoinColumn(name = "session_id"))
	@MapKeyColumn(name = "player_position")
	@Column(name = "api_key")
	private Map<Integer, String> playerApiKeys = new HashMap<>(); // position -> apiKey

	public GameSession() {
	}

	public GameSession(String adminKey) {
		this.sessionId = UUID.randomUUID();
		this.adminKey = adminKey;
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public String getAdminKey() {
		return adminKey;
	}

	public String getGameStateJson() {
		return gameStateJson;
	}

	public void setGameStateJson(String gameStateJson) {
		this.gameStateJson = gameStateJson;
		this.updatedAt = Instant.now();
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Map<Integer, String> getPlayerApiKeys() {
		return playerApiKeys;
	}

	public void setPlayerApiKeys(Map<Integer, String> playerApiKeys) {
		this.playerApiKeys = playerApiKeys;
	}

	public void addPlayerApiKey(int playerPosition, String apiKey) {
		this.playerApiKeys.put(playerPosition, apiKey);
		this.updatedAt = Instant.now();
	}

	public String getPlayerApiKey(int playerPosition) {
		return this.playerApiKeys.get(playerPosition);
	}

	public boolean validatePlayerKey(int playerPosition, String apiKey) {
		String storedKey = this.playerApiKeys.get(playerPosition);
		return storedKey != null && storedKey.equals(apiKey);
	}

	public boolean validateAdminKey(String key) {
		return this.adminKey.equals(key);
	}
}
