package com.pauletchells.europa.domain;

public enum PlayerType {
	HUMAN("Human"),
	CPU("Computer"),
	NONE("Not Playing");

	private final String displayName;

	PlayerType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
