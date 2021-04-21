package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Location;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import java.util.List;

public interface StarterPackService {

  Location generateKingdomLocation(Kingdom kingdom);

  List<Resource> setStartingResources(Kingdom kingdom);

  List<Building> setStartingBuildings(Kingdom kingdom);
}
