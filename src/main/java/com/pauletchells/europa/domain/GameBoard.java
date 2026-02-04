package com.pauletchells.europa.domain;

public class GameBoard {
	private static final int TOTAL_TERRITORIES = 16;
	private static final int TOTAL_LOCATIONS = 25;
	private final Territory[] territories;
	private final HomeBase[] homeBases; // 5 player home bases + 2 expansion placeholders
	private final EncounterToken[] encounterTokens; // 11 encounter tokens placed on marked territories

	public GameBoard() {
		this.territories = new Territory[TOTAL_LOCATIONS];
		this.homeBases = new HomeBase[7];
		this.encounterTokens = new EncounterToken[11];
		initializeBoard();
		initializeHomeBases();
		initializeEncounters();
	}

	private void initializeBoard() {
		// Initialize 16 playable territories with varied terrain types
		// The board layout follows the Scythe game board
		territories[0] = new Territory(0, "Territory 1", TerrainType.FARM);
		territories[1] = new Territory(1, "Territory 2", TerrainType.FOREST);
		territories[2] = new Territory(2, "Territory 3", TerrainType.MOUNTAIN);
		territories[3] = new Territory(3, "Territory 4", TerrainType.FARM);
		territories[4] = new Territory(4, "Territory 5", TerrainType.VILLAGE);
		territories[5] = new Territory(5, "Territory 6", TerrainType.FOREST);
		territories[6] = new Territory(6, "Territory 7", TerrainType.TUNDRA);
		territories[7] = new Territory(7, "Territory 8", TerrainType.MOUNTAIN);
		territories[8] = new Territory(8, "Territory 9", TerrainType.FARM);
		territories[9] = new Territory(9, "Territory 10", TerrainType.FOREST);
		territories[10] = new Territory(10, "Territory 11", TerrainType.VILLAGE);
		territories[11] = new Territory(11, "Territory 12", TerrainType.TUNDRA);
		territories[12] = new Territory(12, "Territory 13", TerrainType.MOUNTAIN);
		territories[13] = new Territory(13, "Territory 14", TerrainType.FARM);
		territories[14] = new Territory(14, "Territory 15", TerrainType.FOREST);
		territories[15] = new Territory(15, "Territory 16", TerrainType.VILLAGE);

		// Initialize 9 special locations (lakes and The Factory)
		territories[16] = new Territory(16, "Lake 1", TerrainType.FACTORY);
		territories[17] = new Territory(17, "Lake 2", null);
		territories[18] = new Territory(18, "Lake 3", null);
		territories[19] = new Territory(19, "Lake 4", null);
		territories[20] = new Territory(20, "Lake 5", null);
		territories[21] = new Territory(21, "Lake 6", null);
		territories[22] = new Territory(22, "Lake 7", null);
		territories[23] = new Territory(23, "Lake 8", null);
		territories[24] = new Territory(24, "Lake 9", null);
	}

	private void initializeEncounters() {
		// Create 11 encounter tokens (descriptions are placeholders)
		for (int i = 0; i < encounterTokens.length; i++) {
			encounterTokens[i] = new EncounterToken(i + 1, "Encounter token " + (i + 1));
		}

		// Assign encounter tokens to territories that would have encounter icons.
		// As a placeholder, assign to the first 11 playable territories (indices
		// 0..10).
		for (int i = 0; i < 11; i++) {
			if (territories[i] != null) {
				territories[i].setEncounterToken(encounterTokens[i]);
			}
		}
	}

	private void initializeHomeBases() {
		// Create 5 player home bases and 2 expansion placeholders
		// IDs 1..7
		homeBases[0] = new HomeBase(1, "Home Base 1", false);
		homeBases[1] = new HomeBase(2, "Home Base 2", false);
		homeBases[2] = new HomeBase(3, "Home Base 3", false);
		homeBases[3] = new HomeBase(4, "Home Base 4", false);
		homeBases[4] = new HomeBase(5, "Home Base 5", false);
		// Expansion placeholders (no pieces)
		homeBases[5] = new HomeBase(6, "Expansion Home Base A", true);
		homeBases[6] = new HomeBase(7, "Expansion Home Base B", true);
	}

	public Territory getTerritory(int index) {
		if (index < 0 || index >= TOTAL_LOCATIONS) {
			throw new IllegalArgumentException("Invalid territory index");
		}
		return territories[index];
	}

	public int getTotalLocations() {
		return TOTAL_LOCATIONS;
	}

	public HomeBase getHomeBase(int id) {
		if (id < 1 || id > homeBases.length) {
			throw new IllegalArgumentException("Invalid home base id");
		}
		return homeBases[id - 1];
	}

	public HomeBase[] getHomeBases() {
		return homeBases.clone();
	}

	public static class Territory {
		private final int id;
		private final String name;
		private final TerrainType terrain;
		private Faction controlledBy;
		private boolean hasStructure;
		private Faction structureOwner;
		// units present on this territory by faction
		private final java.util.Map<Faction, UnitPresence> unitsByFaction = new java.util.HashMap<>();
		private int resourceCount;
		private EncounterToken encounterToken;

		public Territory(int id, String name, TerrainType terrain) {
			this.id = id;
			this.name = name;
			this.terrain = terrain;
			this.controlledBy = null;
			this.hasStructure = false;
			this.structureOwner = null;
			// initialize unit presence map
			for (Faction f : Faction.values()) {
				unitsByFaction.put(f, new UnitPresence());
			}
			this.resourceCount = 0;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public TerrainType getTerrain() {
			return terrain;
		}

		public Faction getControlledBy() {
			return controlledBy;
		}

		public void setControlledBy(Faction faction) {
			this.controlledBy = faction;
		}

		public boolean hasStructure() {
			return hasStructure;
		}

		public Faction getStructureOwner() {
			return structureOwner;
		}

		public void setStructure(boolean hasStructure) {
			this.hasStructure = hasStructure;
			if (!hasStructure)
				this.structureOwner = null;
		}

		public void setStructureOwner(Faction owner) {
			this.structureOwner = owner;
			this.hasStructure = owner != null;
		}

		public int getResourceCount() {
			return resourceCount;
		}

		public void setResourceCount(int count) {
			this.resourceCount = Math.max(0, count);
		}

		public EncounterToken getEncounterToken() {
			return encounterToken;
		}

		public void setEncounterToken(EncounterToken token) {
			this.encounterToken = token;
		}

		public void removeEncounterToken() {
			this.encounterToken = null;
		}

		public java.util.Map<Faction, UnitPresence> getUnitsByFaction() {
			return java.util.Collections.unmodifiableMap(unitsByFaction);
		}

		public void placeUnit(Faction faction, UnitType type) {
			UnitPresence p = unitsByFaction.computeIfAbsent(faction, k -> new UnitPresence());
			switch (type) {
				case CHARACTER:
					p.characterPresent = true;
					break;
				case MECH:
					p.mechs++;
					break;
				case WORKER:
					p.workers++;
					break;
			}
		}

		public void removeUnit(Faction faction, UnitType type) {
			UnitPresence p = unitsByFaction.get(faction);
			if (p == null)
				return;
			switch (type) {
				case CHARACTER:
					p.characterPresent = false;
					break;
				case MECH:
					p.mechs = Math.max(0, p.mechs - 1);
					break;
				case WORKER:
					p.workers = Math.max(0, p.workers - 1);
					break;
			}
		}

		public Faction getControllingFaction() {
			java.util.List<Faction> present = new java.util.ArrayList<>();
			for (java.util.Map.Entry<Faction, UnitPresence> e : unitsByFaction.entrySet()) {
				if (e.getValue().totalUnits() > 0) {
					present.add(e.getKey());
				}
			}

			if (present.size() == 1) {
				return present.get(0);
			} else if (present.size() > 1) {
				// Contested by multiple factions — no single controller
				return null;
			}
			// No units present: control by structure owner if present
			if (hasStructure && structureOwner != null) {
				return structureOwner;
			}
			return null;
		}

		private static class UnitPresence {
			int workers = 0;
			int mechs = 0;
			boolean characterPresent = false;

			int totalUnits() {
				int total = workers + mechs + (characterPresent ? 1 : 0);
				return total;
			}
		}
	}
}
