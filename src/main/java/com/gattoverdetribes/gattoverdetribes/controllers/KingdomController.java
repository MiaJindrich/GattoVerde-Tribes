package com.gattoverdetribes.gattoverdetribes.controllers;

import com.gattoverdetribes.gattoverdetribes.dtos.KingdomDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.services.KingdomService;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class KingdomController {

  private final Logger logger;
  private final KingdomService kingdomService;
  private final PlayerService playerService;

  @Autowired
  public KingdomController(Logger logger, KingdomService kingdomService,
      PlayerService playerService) {
    this.logger = logger;
    this.kingdomService = kingdomService;
    this.playerService = playerService;
  }

  @GetMapping("/kingdoms")
  public ResponseEntity<List<KingdomDetailsDTO>> getKingdoms(
      @RequestHeader(name = "X-tribes-token") String token) {
    List<KingdomDetailsDTO> result = kingdomService.getKingdoms();
    Player player = playerService.extractPlayerFromToken(token);

    logger.info("Player with id " + player.getId() + " and username " + player.getUsername()
        + " reached /kingdom/kingdoms endpoint with Get request method.");
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
