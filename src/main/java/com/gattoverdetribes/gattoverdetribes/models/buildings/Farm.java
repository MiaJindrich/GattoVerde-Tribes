package com.gattoverdetribes.gattoverdetribes.models.buildings;

import javax.persistence.Entity;

@Entity
public class Farm extends Building {

  public Farm() {
    setType(BuildingType.FARM);
    super.fillDefaults(BuildingType.FARM.toString().toLowerCase());
  }
}