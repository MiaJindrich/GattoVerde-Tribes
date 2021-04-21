package com.gattoverdetribes.gattoverdetribes.services;

import static com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingFactory.buildBuilding;

import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import com.gattoverdetribes.gattoverdetribes.dtos.BuildingDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.CreateBuildingResponseDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.IdNotFoundException;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingException;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingLevelException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingParameterException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingResourceException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NoContentException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NotEnoughResourcesException;
import com.gattoverdetribes.gattoverdetribes.mappers.Mapper;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingType;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.repositories.BuildingRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingServiceImpl implements BuildingService {

  private final BuildingRepository buildingRepository;
  private final ResourceService resourceService;
  private final Mapper mapper;

  @Autowired
  public BuildingServiceImpl(
      BuildingRepository buildingRepository,
      ResourceService resourceService, Mapper mapper) {
    this.buildingRepository = buildingRepository;
    this.resourceService = resourceService;
    this.mapper = mapper;
  }

  public Building checkOptionalBuilding(Long id) {
    if (buildingRepository.findById(id).isPresent()) {
      return buildingRepository.findById(id).get();
    }
    throw new IdNotFoundException();
  }

  @Override
  public void validateBuildingType(String type) {
    if (type == null || type.isEmpty()) {
      throw new MissingParameterException("Fill in building type you must.");
    }

    List<BuildingType> buildingTypes = Arrays.asList(BuildingType.values());
    List<String> buildings = buildingTypes.stream().map(t -> t.toString().toLowerCase())
        .collect(Collectors.toList());
    boolean containsType = buildings.contains(type.toLowerCase());

    if (!containsType) {
      throw new InvalidBuildingException("Created such building can not be. Yrsssss.");
    } else if (type.toLowerCase().equals("townhall")) {
      throw new InvalidBuildingException("Only one townhall kingdom can have. Yes, hrrrm.");
    }
  }

  @Override
  public Building createBuilding(String buildingType, Kingdom kingdom) {
    if (kingdom.getBuildings() != null) {
      validateBuildingType(buildingType);
    }
    Building building = buildBuilding(buildingType);
    building.setLevel(ExternalConfig.getInstance().getBuildingStartLevel());
    building.setKingdom(kingdom);
    saveBuilding(building);
    return building;
  }

  @Override
  public CreateBuildingResponseDTO purchaseBuilding(String type, Player player) {
    Building building = createBuilding(type.toUpperCase(), player.getKingdom());
    List<Building> buildings = buildingRepository.findAllByKingdomId(player.getKingdom().getId());
    player.getKingdom().setBuildings(buildings);
    return mapper.buildingToCreateBuildingResponseDto(building);
  }

  @Override
  public List<BuildingDetailsDTO> getBuildingsByKingdom(Kingdom kingdom) throws NoContentException {
    List<Building> buildings = kingdom.getBuildings();
    if (buildings.isEmpty()) {
      throw new NoContentException("I sense no buildings in this kingdom.");
    }
    return mapper.modelToDto(buildings);
  }

  @Override
  public Building getBuilding(Kingdom kingdom, String type) throws EntityNotFoundException {
    return kingdom.getBuildings().stream()
        .filter(b -> b.getType().toString().toLowerCase().equals(type.toLowerCase()))
        .findAny()
        .orElseThrow(() -> new MissingResourceException("Building cannot be found!"));
  }

  @Override
  public void saveBuilding(Building building) {
    buildingRepository.save(building);
  }

  @Override
  public CreateBuildingResponseDTO upgradeBuilding(Player player, Long id)
      throws NotEnoughResourcesException {
    Building building = checkOptionalBuilding(id);
    resourceService.upgradeBuildingCheckSufficientResources(player.getKingdom());
    isBuildingUpgradeable(building);
    upgradeLevel(building);
    List<Building> buildings = buildingRepository.findAllByKingdomId(player.getKingdom().getId());
    player.getKingdom().setBuildings(buildings);
    return mapper.buildingToCreateBuildingResponseDto(building);
  }

  public void upgradeLevel(Building building) {
    Integer level = building.getLevel();
    level = level + 1;
    building.setLevel(level);
    saveBuilding(building);
  }

  public void isBuildingUpgradeable(Building building) {
    Integer currentBuildingLevel = building.getLevel();
    Kingdom kingdom = building.getKingdom();
    List<Building> kingdomsBuildings = buildingRepository.findAllByKingdomId(kingdom.getId());
    boolean isTownhall = building.getType().equals(BuildingType.TOWNHALL);
    Integer townhallLevel = 0;

    for (Building selectedBuilding : kingdomsBuildings) {
      if (selectedBuilding.getType().equals(BuildingType.TOWNHALL)) {
        townhallLevel = selectedBuilding.getLevel();
      }
    }

    if (isTownhall && townhallLevel >= 20) {
      throw new InvalidBuildingLevelException("Townhall has already reached level 20.");
    }
    if (!isTownhall && currentBuildingLevel >= townhallLevel) {
      throw new InvalidBuildingLevelException(
          "Building level cannot be higher than Townhall level.");
    }
  }
}
