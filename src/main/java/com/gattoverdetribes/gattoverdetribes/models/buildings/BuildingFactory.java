package com.gattoverdetribes.gattoverdetribes.models.buildings;

import static com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingType.generateBuilding;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingException;

public class BuildingFactory {

  public static Building buildBuilding(String buildingType) throws InvalidBuildingException {
    for (BuildingType type : BuildingType.values()) {
      if (BuildingType.valueOf(buildingType.toUpperCase()).equals(type)) {
        return generateBuilding(type);
      }
    }
    throw new InvalidBuildingException("Created such building can not be. Yrsssss.");
  }
}