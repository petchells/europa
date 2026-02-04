package com.pauletchells.europa.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Deck<T extends Card> {
	private final List<T> cards;
	private final List<T> discardPile;
	private int currentIndex;

	public Deck(List<T> initialCards) {
		this.cards = new ArrayList<>(initialCards);
		this.discardPile = new ArrayList<>();
		this.currentIndex = 0;
	}

	public Optional<T> drawCard() {
		if (currentIndex < cards.size()) {
			return Optional.of(cards.get(currentIndex++));
		}
		return Optional.empty();
	}

	public void discardCard(T card) {
		discardPile.add(card);
	}

	public void reshuffleDeck() {
		cards.addAll(discardPile);
		discardPile.clear();
		currentIndex = 0;
		Collections.shuffle(cards);
	}

	public int getRemainingCards() {
		return cards.size() - currentIndex;
	}

	public int getDiscardPileSize() {
		return discardPile.size();
	}

	public boolean isEmpty() {
		return currentIndex >= cards.size();
	}
}
