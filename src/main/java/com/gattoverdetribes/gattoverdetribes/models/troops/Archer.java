package com.gattoverdetribes.gattoverdetribes.models.troops;

import javax.persistence.Entity;

@Entity
public class Archer extends Troop {

  public Archer() {
    this.setType(TroopType.ARCHER);
    super.fillDefaults(TroopType.ARCHER.toString().toLowerCase());
  }
}