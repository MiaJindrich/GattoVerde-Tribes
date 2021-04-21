package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.dtos.BuildingDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.CreateBuildingResponseDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.NoContentException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NotEnoughResourcesException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import java.util.List;
import javax.persistence.EntityNotFoundException;

public interface BuildingService {

  Building createBuilding(String buildingType, Kingdom kingdom);

  List<BuildingDetailsDTO> getBuildingsByKingdom(Kingdom kingdom) throws NoContentException;

  Building getBuilding(Kingdom kingdom, String type) throws EntityNotFoundException;

  void validateBuildingType(String type);

  CreateBuildingResponseDTO purchaseBuilding(String type, Player player);

  CreateBuildingResponseDTO upgradeBuilding(Player player, Long id)
      throws NotEnoughResourcesException;

  void upgradeLevel(Building building);

  void isBuildingUpgradeable(Building building);

  void saveBuilding(Building building);
}