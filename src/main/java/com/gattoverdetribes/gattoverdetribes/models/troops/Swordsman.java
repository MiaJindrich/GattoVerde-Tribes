package com.gattoverdetribes.gattoverdetribes.models.troops;

import javax.persistence.Entity;

@Entity
public class Swordsman extends Troop {

  public Swordsman() {
    setType(TroopType.SWORDSMAN);
    super.fillDefaults(TroopType.SWORDSMAN.toString().toLowerCase());
  }
}