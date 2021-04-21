package com.gattoverdetribes.gattoverdetribes.models.buildings;

public enum BuildingType {

  ACADEMY,
  TOWNHALL,
  FARM,
  MINE;

  public static Building generateBuilding(BuildingType buildingType) {
    switch (buildingType) {
      case ACADEMY:
        return new Academy();
      case TOWNHALL:
        return new TownHall();
      case FARM:
        return new Farm();
      case MINE:
        return new Mine();
      default:
        return null;
    }
  }
}