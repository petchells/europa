package com.pauletchells.europa.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pauletchells.europa.domain.GameBoard;
import com.pauletchells.europa.service.GameService;

@RestController
@RequestMapping("/api/board")
public class BoardController {

	private final GameService gameService;

	public BoardController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping("/territory/{index}")
	public ResponseEntity<GameBoard.Territory> getTerritory(@PathVariable int index) {
		GameBoard board = gameService.getGameBoard();
		if (board == null) {
			return ResponseEntity.notFound().build();
		}
		try {
			GameBoard.Territory territory = board.getTerritory(index);
			return ResponseEntity.ok(territory);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/territories")
	public ResponseEntity<java.util.List<GameBoard.Territory>> getAllTerritories() {
		GameBoard board = gameService.getGameBoard();
		if (board == null) {
			return ResponseEntity.notFound().build();
		}

		java.util.List<GameBoard.Territory> territories = new java.util.ArrayList<>();
		for (int i = 0; i < board.getTotalLocations(); i++) {
			territories.add(board.getTerritory(i));
		}
		return ResponseEntity.ok(territories);
	}

	@PostMapping("/territory/{index}/control")
	public ResponseEntity<GameBoard.Territory> setTerritoryControl(
			@PathVariable int index,
			@RequestParam String factionName) {
		GameBoard board = gameService.getGameBoard();
		if (board == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			GameBoard.Territory territory = board.getTerritory(index);
			com.pauletchells.europa.domain.Faction faction = com.pauletchells.europa.domain.Faction
					.valueOf(factionName.toUpperCase());
			territory.setControlledBy(faction);
			return ResponseEntity.ok(territory);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/territory/{index}/structure")
	public ResponseEntity<GameBoard.Territory> buildStructure(@PathVariable int index) {
		GameBoard board = gameService.getGameBoard();
		if (board == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			GameBoard.Territory territory = board.getTerritory(index);
			territory.setStructure(true);
			return ResponseEntity.ok(territory);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/territory/{index}/remove-structure")
	public ResponseEntity<GameBoard.Territory> removeStructure(@PathVariable int index) {
		GameBoard board = gameService.getGameBoard();
		if (board == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			GameBoard.Territory territory = board.getTerritory(index);
			territory.setStructure(false);
			return ResponseEntity.ok(territory);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/territory/{index}/resources")
	public ResponseEntity<GameBoard.Territory> setResourceCount(
			@PathVariable int index,
			@RequestParam int count) {
		GameBoard board = gameService.getGameBoard();
		if (board == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			GameBoard.Territory territory = board.getTerritory(index);
			territory.setResourceCount(count);
			return ResponseEntity.ok(territory);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// Home base endpoints
	@GetMapping("/homebase/{id}")
	public ResponseEntity<com.pauletchells.europa.domain.HomeBase> getHomeBase(@PathVariable int id) {
		com.pauletchells.europa.domain.HomeBase hb = gameService.getHomeBase(id);
		if (hb == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(hb);
	}

	@GetMapping("/homebases")
	public ResponseEntity<com.pauletchells.europa.domain.HomeBase[]> getHomeBases() {
		com.pauletchells.europa.domain.HomeBase[] hbs = gameService.getHomeBases();
		if (hbs == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(hbs);
	}

	@PostMapping("/homebase/{id}/owner")
	public ResponseEntity<com.pauletchells.europa.domain.HomeBase> setHomeBaseOwner(
			@PathVariable int id,
			@RequestParam String factionName) {
		try {
			com.pauletchells.europa.domain.Faction faction = com.pauletchells.europa.domain.Faction
					.valueOf(factionName.toUpperCase());
			boolean ok = gameService.setHomeBaseOwner(id, faction);
			if (!ok)
				return ResponseEntity.badRequest().build();
			return ResponseEntity.ok(gameService.getHomeBase(id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/territory/{index}/place-unit")
	public ResponseEntity<?> placeUnitOnTerritory(
			@PathVariable int index,
			@RequestParam int playerPosition,
			@RequestParam String type) {
		try {
			com.pauletchells.europa.domain.UnitType ut = com.pauletchells.europa.domain.UnitType
					.valueOf(type.toUpperCase());
			boolean ok = gameService.placeUnitOnTerritory(playerPosition, index, ut);
			if (!ok)
				return ResponseEntity.badRequest().body("Cannot place unit");
			return ResponseEntity.ok(gameService.getGameBoard().getTerritory(index));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/territory/{index}/remove-unit")
	public ResponseEntity<?> removeUnitFromTerritory(
			@PathVariable int index,
			@RequestParam int playerPosition,
			@RequestParam String type) {
		try {
			com.pauletchells.europa.domain.UnitType ut = com.pauletchells.europa.domain.UnitType
					.valueOf(type.toUpperCase());
			boolean ok = gameService.removeUnitFromTerritory(playerPosition, index, ut);
			if (!ok)
				return ResponseEntity.badRequest().body("Cannot remove unit");
			return ResponseEntity.ok(gameService.getGameBoard().getTerritory(index));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/territory/{index}/spend-resources")
	public ResponseEntity<?> spendResourcesFromTerritory(
			@PathVariable int index,
			@RequestParam int playerPosition,
			@RequestParam int count) {
		boolean ok = gameService.spendResourcesFromTerritory(playerPosition, index, count);
		if (!ok)
			return ResponseEntity.badRequest().body("Cannot spend resources from this territory");
		return ResponseEntity.ok(gameService.getGameBoard().getTerritory(index));
	}
}
