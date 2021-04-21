package com.gattoverdetribes.gattoverdetribes.controllers;

import com.gattoverdetribes.gattoverdetribes.dtos.TroopDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import com.gattoverdetribes.gattoverdetribes.services.TroopService;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kingdom")
public class TroopController {

  private final Logger logger;
  private final PlayerService playerService;
  private final TroopService troopService;

  @Autowired
  public TroopController(Logger logger, PlayerService playerService,
      TroopService troopService) {
    this.logger = logger;
    this.playerService = playerService;
    this.troopService = troopService;
  }

  @GetMapping("/troops")
  public ResponseEntity<List<TroopDetailsDTO>> getTroopsByKingdom(
      @RequestHeader(name = "X-tribes-token") String token) {
    Kingdom kingdom = playerService.extractPlayerFromToken(token).getKingdom();
    List<TroopDetailsDTO> listTroops = troopService.getTroopsByKingdom(kingdom);
    Player player = playerService.extractPlayerFromToken(token);

    logger.info("Player with id " + player.getId() + " and username " + player.getUsername()
        + " reached /kingdom/troops endpoint with Get request method.");
    return new ResponseEntity<>(listTroops, HttpStatus.OK);
  }
}
