package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Location;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingType;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.models.resources.ResourceType;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarterPackServiceImpl implements StarterPackService {

  private final KingdomRepository kingdomRepository;
  private final ResourceServiceImpl resourceService;
  private final BuildingService buildingService;

  @Autowired
  public StarterPackServiceImpl(
      KingdomRepository kingdomRepository,
      ResourceServiceImpl resourceService,
      BuildingService buildingService) {
    this.kingdomRepository = kingdomRepository;
    this.resourceService = resourceService;
    this.buildingService = buildingService;
  }

  @Override
  public Location generateKingdomLocation(Kingdom kingdom) {
    Random rand = new Random();
    int xcord = rand.nextInt(100);
    int ycord = rand.nextInt(100);
    List<Kingdom> kingdoms = kingdomRepository.findAll();
    List<Location> locations = kingdoms.stream().map(Kingdom::getLocation)
        .collect(Collectors.toList());
    Location newLocation = new Location(xcord, ycord);
    if (locations.stream().allMatch(Objects::isNull)) {
      newLocation.setKingdom(kingdom);
      return newLocation;
    }
    for (int i = 0; i < locations.size(); i++) {
      if (locations.get(i) != null && locations.get(i).equals(newLocation)) {
        xcord = rand.nextInt(100);
        ycord = rand.nextInt(100);
        newLocation.setXcord(xcord);
        newLocation.setYcord(ycord);
        i = 0;
      }
    }
    newLocation.setKingdom(kingdom);
    return newLocation;
  }

  @Override
  public List<Resource> setStartingResources(Kingdom kingdom) {
    List<String> resourcesToCreate = Stream.of(ResourceType.values())
        .map(Enum::name).collect(
            Collectors.toList());
    if (!resourcesToCreate.isEmpty()) {
      List<Resource> resources = new ArrayList<>();
      for (String resourceType : resourcesToCreate) {
        resources.add(resourceService.createResource(resourceType, kingdom));
      }
      return resources;
    }
    return null;
  }

  @Override
  public List<Building> setStartingBuildings(Kingdom kingdom) {
    List<String> buildingsToBuild = Stream.of(BuildingType.values())
        .map(Enum::name)
        .collect(Collectors.toList());
    if (!buildingsToBuild.isEmpty()) {
      List<Building> buildings = new ArrayList<>();
      for (String buildingType : buildingsToBuild) {
        buildings.add(buildingService.createBuilding(buildingType, kingdom));
      }
      return buildings;
    }
    return null;
  }
}
