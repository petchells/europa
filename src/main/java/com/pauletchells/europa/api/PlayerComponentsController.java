package com.pauletchells.europa.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pauletchells.europa.domain.PlayerComponents;
import com.pauletchells.europa.service.GameService;

@RestController
@RequestMapping("/api/player/{playerPosition}/components")
public class PlayerComponentsController {

	private final GameService gameService;

	public PlayerComponentsController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping
	public ResponseEntity<PlayerComponents> getPlayerComponents(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(components);
	}

	@PostMapping("/place-star")
	public ResponseEntity<PlayerComponents> placeStarToken(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.placeStarToken();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/place-structure")
	public ResponseEntity<PlayerComponents> placeStructureToken(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.placeStructureToken();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/place-recruit")
	public ResponseEntity<PlayerComponents> placeRecruitToken(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.placeRecruitToken();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/place-mech")
	public ResponseEntity<PlayerComponents> placeMechToken(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.placeMechToken();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/place-worker")
	public ResponseEntity<PlayerComponents> placeWorkerToken(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.placeWorkerToken();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/use-tech-cube")
	public ResponseEntity<PlayerComponents> useTechnologyCube(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.useTechnologyCube();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/use-action-token")
	public ResponseEntity<PlayerComponents> useActionToken(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.useActionToken();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/set-popularity")
	public ResponseEntity<PlayerComponents> setPopularityTrack(@PathVariable int playerPosition,
			@RequestParam int position) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.setPopularityTrack(position);
		return ResponseEntity.ok(components);
	}

	@PostMapping("/move-popularity")
	public ResponseEntity<PlayerComponents> movePopularityTrack(@PathVariable int playerPosition,
			@RequestParam int amount) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.movePopularityTrack(amount);
		return ResponseEntity.ok(components);
	}

	@PostMapping("/set-power")
	public ResponseEntity<PlayerComponents> setPowerTrack(@PathVariable int playerPosition,
			@RequestParam int position) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.setPowerTrack(position);
		return ResponseEntity.ok(components);
	}

	@PostMapping("/move-power")
	public ResponseEntity<PlayerComponents> movePowerTrack(@PathVariable int playerPosition, @RequestParam int amount) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.movePowerTrack(amount);
		return ResponseEntity.ok(components);
	}

	@PostMapping("/place-character")
	public ResponseEntity<PlayerComponents> placeCharacter(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.placeCharacterMiniature();
		return ResponseEntity.ok(components);
	}

	@PostMapping("/remove-character")
	public ResponseEntity<PlayerComponents> removeCharacter(@PathVariable int playerPosition) {
		PlayerComponents components = gameService.getPlayerComponents(playerPosition);
		if (components == null) {
			return ResponseEntity.notFound().build();
		}
		components.removeCharacterMiniature();
		return ResponseEntity.ok(components);
	}
}
