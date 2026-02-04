package com.pauletchells.europa.domain;

public class Card {
	private final String id;
	private final CardType type;
	private final String title;
	private final String description;

	public Card(String id, CardType type, String title, String description) {
		this.id = id;
		this.type = type;
		this.title = title;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public CardType getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public enum CardType {
		COMBAT("Combat Card", 42),
		OBJECTIVE("Objective Card", 23),
		ENCOUNTER("Encounter Card", 28),
		FACTORY("Factory Card", 12);

		private final String displayName;
		private final int totalInDeck;

		CardType(String displayName, int totalInDeck) {
			this.displayName = displayName;
			this.totalInDeck = totalInDeck;
		}

		public String getDisplayName() {
			return displayName;
		}

		public int getTotalInDeck() {
			return totalInDeck;
		}
	}
}
