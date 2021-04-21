package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.dtos.ResourceDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.NotEnoughResourcesException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import java.util.List;

public interface ResourceService {

  Resource createResource(String resourceType, Kingdom kingdom);

  void validateResourceType(String type);

  void saveResource(Resource resource);

  List<ResourceDetailsDTO> getResourcesByKingdom(Kingdom kingdom);

  void buildBuildingCheckSufficientResources(Kingdom kingdom) throws NotEnoughResourcesException;

  void upgradeBuildingCheckSufficientResources(Kingdom kingdom) throws NotEnoughResourcesException;

  void calculateKingdomsProduction();

  Resource getResource(Kingdom kingdom, String type);
}
