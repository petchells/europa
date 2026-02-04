package com.pauletchells.europa.domain;

public enum TerrainType {
	// Primary terrain types
	FARM("Farm", "Agricultural land producing resources", true),
	FOREST("Forest", "Wooded area with timber resources", true),
	MOUNTAIN("Mountain", "Rocky terrain with mineral resources", true),
	TUNDRA("Tundra", "Cold frozen wasteland", true),
	VILLAGE("Village", "Populated settlement", true),

	// Special terrain type
	FACTORY("Factory", "Industrial complex - The Factory", false);

	private final String displayName;
	private final String description;
	private final boolean isPrimaryTerrain;

	TerrainType(String displayName, String description, boolean isPrimaryTerrain) {
		this.displayName = displayName;
		this.description = description;
		this.isPrimaryTerrain = isPrimaryTerrain;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public boolean isPrimaryTerrain() {
		return isPrimaryTerrain;
	}
}
