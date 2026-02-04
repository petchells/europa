package com.pauletchells.europa.domain;

/**
 * A HomeBase represents a player's home base on the board. It is NOT a
 * territory.
 * Home bases cannot have units moved onto them, structures built, or mechs
 * deployed by default.
 * Expansion home bases are placeholders without corresponding pieces.
 */
public class HomeBase {
	private final int id; // unique id for home base
	private final String name;
	private Faction owner; // null when unclaimed / expansion placeholder
	private final boolean expansionPlaceholder;

	public HomeBase(int id, String name, boolean expansionPlaceholder) {
		this.id = id;
		this.name = name;
		this.owner = null;
		this.expansionPlaceholder = expansionPlaceholder;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Faction getOwner() {
		return owner;
	}

	public void setOwner(Faction owner) {
		this.owner = owner;
	}

	public boolean isExpansionPlaceholder() {
		return expansionPlaceholder;
	}
}
