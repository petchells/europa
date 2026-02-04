package com.pauletchells.europa.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pauletchells.europa.domain.EndGameScore;
import com.pauletchells.europa.domain.PlayerProgress;
import com.pauletchells.europa.service.GameService;
import com.pauletchells.europa.service.ScoringService;

@RestController
@RequestMapping("/api/player")
public class PlayerProgressController {

	private final GameService gameService;
	private final ScoringService scoringService;

	public PlayerProgressController(GameService gameService, ScoringService scoringService) {
		this.gameService = gameService;
		this.scoringService = scoringService;
	}

	@GetMapping("/{playerPosition}/progress")
	public ResponseEntity<PlayerProgress> getPlayerProgress(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.getPlayerProgress(playerPosition);
		if (progress == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/upgrade")
	public ResponseEntity<PlayerProgress> addUpgrade(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.addUpgrade(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/mech")
	public ResponseEntity<PlayerProgress> addMech(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.addMech(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/structure")
	public ResponseEntity<PlayerProgress> addStructure(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.addStructure(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/recruit")
	public ResponseEntity<PlayerProgress> addRecruit(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.addRecruit(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/worker")
	public ResponseEntity<PlayerProgress> addWorker(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.addWorker(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/combat-win")
	public ResponseEntity<PlayerProgress> addCombatWin(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.addCombatWin(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/complete-objective")
	public ResponseEntity<PlayerProgress> completeObjective(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.completeObjective(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/popularity")
	public ResponseEntity<PlayerProgress> setPopularity(@PathVariable int playerPosition, @RequestParam int value) {
		PlayerProgress progress = gameService.setPopularity(playerPosition, value);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/power")
	public ResponseEntity<PlayerProgress> setPower(@PathVariable int playerPosition, @RequestParam int value) {
		PlayerProgress progress = gameService.setPower(playerPosition, value);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/coins")
	public ResponseEntity<PlayerProgress> addCoins(@PathVariable int playerPosition, @RequestParam int amount) {
		PlayerProgress progress = gameService.getPlayerProgress(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		progress.addCoins(amount);
		gameService.updatePlayerProgress(playerPosition, progress);
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/territories")
	public ResponseEntity<PlayerProgress> setTerritoriesControlled(@PathVariable int playerPosition,
			@RequestParam int count) {
		PlayerProgress progress = gameService.getPlayerProgress(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		progress.setTerritoriesControlled(count);
		gameService.updatePlayerProgress(playerPosition, progress);
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/resources")
	public ResponseEntity<PlayerProgress> setResourcesControlled(@PathVariable int playerPosition,
			@RequestParam int count) {
		PlayerProgress progress = gameService.getPlayerProgress(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		progress.setResourcesControlled(count);
		gameService.updatePlayerProgress(playerPosition, progress);
		return ResponseEntity.ok(progress);
	}

	@PostMapping("/{playerPosition}/structure-bonus")
	public ResponseEntity<PlayerProgress> addStructureBonus(@PathVariable int playerPosition, @RequestParam int bonus) {
		PlayerProgress progress = gameService.getPlayerProgress(playerPosition);
		if (progress == null) {
			return ResponseEntity.badRequest().build();
		}
		progress.addStructureBonus(bonus);
		gameService.updatePlayerProgress(playerPosition, progress);
		return ResponseEntity.ok(progress);
	}

	@GetMapping("/{playerPosition}/end-game-score")
	public ResponseEntity<EndGameScore> getEndGameScore(@PathVariable int playerPosition) {
		PlayerProgress progress = gameService.getPlayerProgress(playerPosition);
		if (progress == null) {
			return ResponseEntity.notFound().build();
		}
		EndGameScore score = scoringService.calculateEndGameScore(progress);
		return ResponseEntity.ok(score);
	}
}