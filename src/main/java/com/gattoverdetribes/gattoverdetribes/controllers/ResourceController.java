package com.gattoverdetribes.gattoverdetribes.controllers;

import com.gattoverdetribes.gattoverdetribes.dtos.ResourceDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import com.gattoverdetribes.gattoverdetribes.services.ResourceService;
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
public class ResourceController {

  private final ResourceService resourceService;
  private final PlayerService playerService;
  private final Logger logger;

  @Autowired
  public ResourceController(
      ResourceService resourceService,
      PlayerService playerService, Logger logger) {
    this.resourceService = resourceService;
    this.playerService = playerService;
    this.logger = logger;
  }

  @GetMapping("/resources")
  public ResponseEntity<List<ResourceDetailsDTO>> getResourcesByKingdom(
      @RequestHeader(name = "X-tribes-token") String token) {
    Player player = playerService.extractPlayerFromToken(token);
    Kingdom kingdom = playerService.extractPlayerFromToken(token).getKingdom();
    List<ResourceDetailsDTO> resources = resourceService.getResourcesByKingdom(kingdom);

    logger.info("Player with id " + player.getId() + " and username " + player.getUsername()
        + " reached /kingdom/resources endpoint with Get request method.");
    return new ResponseEntity<>(resources, HttpStatus.OK);
  }
}