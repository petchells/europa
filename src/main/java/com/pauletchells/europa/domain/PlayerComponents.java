package com.pauletchells.europa.domain;

public class PlayerComponents {
	// Token limits
	public static final int STAR_TOKENS = 6;
	public static final int STRUCTURE_TOKENS = 4;
	public static final int RECRUIT_TOKENS = 4;
	public static final int MECH_TOKENS = 4;
	public static final int WORKER_TOKENS = 8;
	public static final int TECHNOLOGY_CUBES = 8;

	// Player components state
	private int starTokensRemaining; // 6 total
	private int structureTokensRemaining; // 4 total
	private int recruitTokensRemaining; // 4 total
	private int mechTokensRemaining; // 4 total
	private int workerTokensRemaining; // 8 total
	private int technologyCubesRemaining; // 8 total

	private int actionTokensUsed; // 1 per turn
	private int currentPopularityTrack; // 0-18 range
	private int currentPowerTrack; // 0+ range
	private boolean characterMiniaturePlaced;

	public PlayerComponents() {
		this.starTokensRemaining = STAR_TOKENS;
		this.structureTokensRemaining = STRUCTURE_TOKENS;
		this.recruitTokensRemaining = RECRUIT_TOKENS;
		this.mechTokensRemaining = MECH_TOKENS;
		this.workerTokensRemaining = WORKER_TOKENS;
		this.technologyCubesRemaining = TECHNOLOGY_CUBES;
		this.actionTokensUsed = 0;
		this.currentPopularityTrack = 0;
		this.currentPowerTrack = 0;
		this.characterMiniaturePlaced = false;
	}

	// Star tokens
	public int getStarTokensRemaining() {
		return starTokensRemaining;
	}

	public void placeStarToken() {
		if (starTokensRemaining > 0) {
			starTokensRemaining--;
		}
	}

	public void resetStarTokens() {
		starTokensRemaining = STAR_TOKENS;
	}

	// Structure tokens
	public int getStructureTokensRemaining() {
		return structureTokensRemaining;
	}

	public void placeStructureToken() {
		if (structureTokensRemaining > 0) {
			structureTokensRemaining--;
		}
	}

	public void resetStructureTokens() {
		structureTokensRemaining = STRUCTURE_TOKENS;
	}

	// Recruit tokens
	public int getRecruitTokensRemaining() {
		return recruitTokensRemaining;
	}

	public void placeRecruitToken() {
		if (recruitTokensRemaining > 0) {
			recruitTokensRemaining--;
		}
	}

	public void resetRecruitTokens() {
		recruitTokensRemaining = RECRUIT_TOKENS;
	}

	// Mech tokens
	public int getMechTokensRemaining() {
		return mechTokensRemaining;
	}

	public void placeMechToken() {
		if (mechTokensRemaining > 0) {
			mechTokensRemaining--;
		}
	}

	public void resetMechTokens() {
		mechTokensRemaining = MECH_TOKENS;
	}

	// Worker tokens
	public int getWorkerTokensRemaining() {
		return workerTokensRemaining;
	}

	public void placeWorkerToken() {
		if (workerTokensRemaining > 0) {
			workerTokensRemaining--;
		}
	}

	public void resetWorkerTokens() {
		workerTokensRemaining = WORKER_TOKENS;
	}

	// Technology cubes
	public int getTechnologyCubesRemaining() {
		return technologyCubesRemaining;
	}

	public void useTechnologyCube() {
		if (technologyCubesRemaining > 0) {
			technologyCubesRemaining--;
		}
	}

	public void resetTechnologyCubes() {
		technologyCubesRemaining = TECHNOLOGY_CUBES;
	}

	// Action tokens
	public int getActionTokensUsed() {
		return actionTokensUsed;
	}

	public void useActionToken() {
		actionTokensUsed++;
	}

	public void resetActionToken() {
		actionTokensUsed = 0;
	}

	// Popularity track
	public int getCurrentPopularityTrack() {
		return currentPopularityTrack;
	}

	public void setPopularityTrack(int position) {
		this.currentPopularityTrack = Math.max(0, Math.min(18, position));
	}

	public void movePopularityTrack(int amount) {
		setPopularityTrack(currentPopularityTrack + amount);
	}

	// Power track
	public int getCurrentPowerTrack() {
		return currentPowerTrack;
	}

	public void setPowerTrack(int position) {
		this.currentPowerTrack = Math.max(0, position);
	}

	public void movePowerTrack(int amount) {
		setPowerTrack(currentPowerTrack + amount);
	}

	// Character miniature
	public boolean isCharacterMiniaturePlaced() {
		return characterMiniaturePlaced;
	}

	public void placeCharacterMiniature() {
		this.characterMiniaturePlaced = true;
	}

	public void removeCharacterMiniature() {
		this.characterMiniaturePlaced = false;
	}
}
