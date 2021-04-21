package com.gattoverdetribes.gattoverdetribes.models.buildings;

import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import javax.persistence.Entity;

@Entity
public class TownHall extends Building {

  public TownHall() {
    setType(BuildingType.TOWNHALL);
    setHp(ExternalConfig.getInstance()
        .getBuildingStartHp().getOrDefault("townhall", 1000));
  }
}
