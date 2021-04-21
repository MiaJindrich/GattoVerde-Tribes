package com.gattoverdetribes.gattoverdetribes.models.troops;

import javax.persistence.Entity;

@Entity
public class Horseman extends Troop {

  public Horseman() {
    setType(TroopType.HORSEMAN);
    super.fillDefaults(TroopType.HORSEMAN.toString().toLowerCase());
  }
}