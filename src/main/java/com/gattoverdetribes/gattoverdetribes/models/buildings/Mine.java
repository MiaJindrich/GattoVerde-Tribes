package com.gattoverdetribes.gattoverdetribes.models.buildings;

import javax.persistence.Entity;

@Entity
public class Mine extends Building {

  public Mine() {
    setType(BuildingType.MINE);
    super.fillDefaults(BuildingType.FARM.toString().toLowerCase());
  }
}
