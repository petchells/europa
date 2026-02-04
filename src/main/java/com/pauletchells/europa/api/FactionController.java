package com.pauletchells.europa.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pauletchells.europa.domain.Faction;
import com.pauletchells.europa.domain.PlayerMat;

@RestController
@RequestMapping("/api/faction")
public class FactionController {

	@GetMapping("/{factionName}/mat")
	public ResponseEntity<PlayerMat> getPlayerMat(@PathVariable String factionName) {
		try {
			Faction faction = Faction.valueOf(factionName.toUpperCase());
			PlayerMat mat = PlayerMat.create(faction);
			return ResponseEntity.ok(mat);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/all")
	public ResponseEntity<Faction[]> getAllFactions() {
		return ResponseEntity.ok(Faction.values());
	}
}
