package com.pauletchells.europa.domain;

public record Player(
		int position,
		PlayerType type,
		String name,
		Faction faction,
		FactionMat factionMat,
		PlayerMat playerMat) {
	public Player(int position, PlayerType type, String name, Faction faction) {
		this(position, type, name, faction,
				faction == null ? null : FactionMat.create(faction),
				faction == null ? null : PlayerMat.create(faction));
	}

	public Player {
		if (position < 1 || position > 5) {
			throw new IllegalArgumentException("Player position must be between 1 and 5");
		}
		if (type == null) {
			throw new IllegalArgumentException("Player type cannot be null");
		}
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Player name cannot be null or empty");
		}
		if (type != PlayerType.NONE && faction == null) {
			throw new IllegalArgumentException("Active players must have a faction");
		}
		if (type != PlayerType.NONE && factionMat == null) {
			throw new IllegalArgumentException("Active players must have a faction mat");
		}
		if (type != PlayerType.NONE && playerMat == null) {
			throw new IllegalArgumentException("Active players must have a player mat");
		}
	}

	public boolean isActive() {
		return type != PlayerType.NONE;
	}
}
