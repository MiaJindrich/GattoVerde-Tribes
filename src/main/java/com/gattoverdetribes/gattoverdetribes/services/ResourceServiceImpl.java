package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import com.gattoverdetribes.gattoverdetribes.dtos.ResourceDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingParameterException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingResourceException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NoContentException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NotEnoughResourcesException;
import com.gattoverdetribes.gattoverdetribes.mappers.Mapper;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingType;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.models.resources.ResourceFactory;
import com.gattoverdetribes.gattoverdetribes.models.resources.ResourceType;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.ResourceRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

  private final ResourceRepository resourceRepository;
  private final KingdomRepository kingdomRepository;
  private final Mapper mapper;

  @Autowired
  public ResourceServiceImpl(
      ResourceRepository resourceRepository,
      KingdomRepository kingdomRepository, Mapper mapper) {
    this.resourceRepository = resourceRepository;
    this.kingdomRepository = kingdomRepository;
    this.mapper = mapper;
  }

  @Override
  public void validateResourceType(String type) {
    if (type == null || type.isEmpty()) {
      throw new MissingParameterException("Fill in resource type you must.");
    }

    List<ResourceType> resourceTypes = Arrays.asList(ResourceType.values());
    List<String> resources = resourceTypes.stream().map(t -> t.toString().toLowerCase())
        .collect(Collectors.toList());
    boolean containsType = resources.contains(type.toLowerCase());

    if (!containsType) {
      throw new InvalidBuildingException("Created such resource can not be. Yrsssss.");
    }
  }

  @Override
  public Resource createResource(String resourceType, Kingdom kingdom) {
    validateResourceType(resourceType);
    Resource resource = ResourceFactory.createResource(resourceType);
    resource.setKingdom(kingdom);
    saveResource(resource);
    return resource;
  }

  @Override
  public void saveResource(Resource resource) {
    resourceRepository.save(resource);
  }

  public List<ResourceDetailsDTO> getResourcesByKingdom(Kingdom kingdom) throws NoContentException {

    List<Resource> resources = resourceRepository.findAllByKingdomId(kingdom.getId());
    if (resources.isEmpty()) {
      throw new NoContentException("I sense no resources in this kingdom.");
    }
    return mapper.resourceToResourceDetailsDTO(resources);
  }

  @Override
  public void buildBuildingCheckSufficientResources(Kingdom kingdom)
      throws NotEnoughResourcesException {
    Integer buildingCost = ExternalConfig.getInstance().getBuildingConstructionCost();
    List<Resource> resources = resourceRepository.findAllByKingdomId(kingdom.getId());
    int goldAmount = resources.stream()
        .filter(r -> r.getType() == ResourceType.GOLD)
        .findAny()
        .map(Resource::getAmount)
        .hashCode();
    if (goldAmount < buildingCost) {
      throw new NotEnoughResourcesException("Enough gold in your treasury there is not.");
    }
  }

  @Override
  public void upgradeBuildingCheckSufficientResources(Kingdom kingdom)
      throws NotEnoughResourcesException {
    Integer upgradeCost = ExternalConfig.getInstance().getBuildingUpgradeCost();
    List<Resource> resources = resourceRepository.findAllByKingdomId(kingdom.getId());
    int goldAmount = resources.stream()
        .filter(r -> r.getType() == ResourceType.GOLD)
        .findAny()
        .map(Resource::getAmount)
        .hashCode();
    if (goldAmount < upgradeCost) {
      throw new NotEnoughResourcesException("Enough gold in your treasury there is not.");
    }
  }

  @Override
  public Resource getResource(Kingdom kingdom, String type) throws EntityNotFoundException {
    return kingdom.getResources().stream()
        .filter(r -> r.getType().toString().toLowerCase().equals(type.toLowerCase()))
        .findAny()
        .orElseThrow(() -> new MissingResourceException("Resource cannot be found!"));
  }

  @Override
  public void calculateKingdomsProduction() {
    if (kingdomRepository.findAll().size() == 0) {
      throw new NoContentException("No kingdoms in this world there are.");
    }
    for (Kingdom kingdom : kingdomRepository.findAll()) {
      if (kingdom.getResources().size() < 2) {
        throw new MissingResourceException(
            "There are some resources missing, I can count only to potato!");
      }
      setKingdomProduction(kingdom);
      List<Resource> resources = kingdom.getResources();
      setResourcesAmount(kingdom, resources);
      resourceRepository.saveAll(resources);
    }
  }

  private void setKingdomProduction(Kingdom kingdom) {
    int goldProduction = 0;
    int foodProduction = 0;
    if (kingdom.getBuildings().size() == 0) {
      throw new NoContentException("No buildings, no production!");
    }
    for (Building building : kingdom.getBuildings()) {
      if (building.getType() == BuildingType.MINE) {
        goldProduction += building.getGoldProduction();
      } else if (building.getType() == BuildingType.FARM) {
        foodProduction += building.getFoodProduction();
      } else if (building.getType() == BuildingType.TOWNHALL) {
        kingdom.setMaxStorage(building.getLevel() * 1000);
        goldProduction += building.getGoldProduction();
        foodProduction += building.getFoodProduction();
      }
      kingdom.setKingdomFoodProduction(foodProduction - kingdom.getTroops().size());
      kingdom.setKingdomGoldProduction(goldProduction);
      kingdomRepository.save(kingdom);
    }
  }

  private void setResourcesAmount(Kingdom kingdom, List<Resource> resources) {
    for (var resource : resources) {
      if (resource.getType() == ResourceType.FOOD) {
        if ((kingdom.getKingdomFoodProduction() + resource.getAmount()) > kingdom.getMaxStorage()) {
          resource.setAmount(kingdom.getMaxStorage());
        }
        resource.setAmount(kingdom.getKingdomFoodProduction() + resource.getAmount());
      } else if (resource.getType() == ResourceType.GOLD) {
        if ((kingdom.getKingdomGoldProduction() + resource.getAmount()) > kingdom.getMaxStorage()) {
          resource.setAmount(kingdom.getMaxStorage());
        }
        resource.setAmount(kingdom.getKingdomGoldProduction() + resource.getAmount());
      }
    }
  }
}
