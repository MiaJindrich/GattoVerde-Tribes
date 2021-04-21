package com.gattoverdetribes.gattoverdetribes.controllers;

import com.gattoverdetribes.gattoverdetribes.dtos.BuildingDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.CreateBuildingRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.CreateBuildingResponseDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.NoContentException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NotEnoughResourcesException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.services.BuildingService;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import com.gattoverdetribes.gattoverdetribes.services.ResourceService;
import org.apache.logging.log4j.Logger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kingdom")
public class BuildingController {

  private final Logger logger;
  private final BuildingService buildingService;
  private final PlayerService playerService;
  private final ResourceService resourceService;

  @Autowired
  public BuildingController(
      Logger logger, BuildingService buildingService,
      PlayerService playerService,
      ResourceService resourceService) {
    this.logger = logger;
    this.buildingService = buildingService;
    this.playerService = playerService;
    this.resourceService = resourceService;
  }

  @GetMapping("/buildings")
  public ResponseEntity<List<BuildingDetailsDTO>> getBuildingsByKingdom(
      @RequestHeader(name = "X-tribes-token") String token) throws NoContentException {
    Kingdom kingdom = playerService.extractPlayerFromToken(token).getKingdom();
    List<BuildingDetailsDTO> buildings = buildingService.getBuildingsByKingdom(kingdom);
    Player player = playerService.extractPlayerFromToken(token);

    logger.info("Player with id " + player.getId() + " and username " + player.getUsername()
        + " reached /kingdom/buildings endpoint with Get request method.");
    return new ResponseEntity<>(buildings, HttpStatus.OK);
  }

  @PostMapping("/buildings")
  public ResponseEntity<?> createBuilding(@RequestHeader(name = "X-tribes-token") String token,
      @RequestBody CreateBuildingRequestDTO createBuildingRequestDTO)
      throws NotEnoughResourcesException {

    Player player = playerService.extractPlayerFromToken(token);

    buildingService.validateBuildingType(createBuildingRequestDTO.getType());
    resourceService.buildBuildingCheckSufficientResources(player.getKingdom());
    CreateBuildingResponseDTO newBuilding = buildingService
        .purchaseBuilding(createBuildingRequestDTO.getType(), player);

    logger.info("Player with id " + player.getId() + " and username " + player.getUsername()
        + " reached /kingdom/buildings endpoint with Post request method. "
        + "The received type parameter was: "
        + createBuildingRequestDTO.getType() + ".");
    return new ResponseEntity<>(newBuilding, HttpStatus.OK);
  }

  @PutMapping("/buildings/{id}")
  public ResponseEntity<?> upgradeBuilding(@RequestHeader(name = "X-tribes-token") String token,
      @PathVariable Long id) throws NotEnoughResourcesException {
    Player player = playerService.extractPlayerFromToken(token);
    CreateBuildingResponseDTO upgradedBuilding = buildingService.upgradeBuilding(player, id);

    logger.info("Player with id " + player.getId() + " and username " + player.getUsername()
        + " reached /kingdom/buildings/{id} endpoint with Put request method. "
        + "The received building id was: " + id + ".");
    return new ResponseEntity<>(upgradedBuilding, HttpStatus.OK);
  }
}
