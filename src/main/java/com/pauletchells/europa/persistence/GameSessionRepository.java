package com.pauletchells.europa.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {
	Optional<GameSession> findByAdminKey(String adminKey);

	Optional<GameSession> findBySessionId(UUID sessionId);
}
