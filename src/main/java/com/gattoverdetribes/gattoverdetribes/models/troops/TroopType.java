package com.gattoverdetribes.gattoverdetribes.models.troops;

public enum TroopType {

  ARCHER,
  HORSEMAN,
  SWORDSMAN;

  public static Troop generateTroop(TroopType troopType) {
    switch (troopType) {
      case ARCHER:
        return new Archer();
      case HORSEMAN:
        return new Horseman();
      case SWORDSMAN:
        return new Swordsman();
      default:
        return null;
    }
  }
}