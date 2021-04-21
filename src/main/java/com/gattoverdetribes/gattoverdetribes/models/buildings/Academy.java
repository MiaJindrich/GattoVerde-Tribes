package com.gattoverdetribes.gattoverdetribes.models.buildings;

import javax.persistence.Entity;

@Entity
public class Academy extends Building {

  public Academy() {
    setType(BuildingType.ACADEMY);
    super.fillDefaults(BuildingType.ACADEMY.toString().toLowerCase());
  }
}
