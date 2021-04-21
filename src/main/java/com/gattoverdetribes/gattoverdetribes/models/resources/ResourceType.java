package com.gattoverdetribes.gattoverdetribes.models.resources;

public enum ResourceType {

  FOOD,
  GOLD;

  public static Resource generateResource(ResourceType resourceType) {
    switch (resourceType) {
      case FOOD:
        return new Food();
      case GOLD:
        return new Gold();
      default:
        return null;
    }
  }
}
