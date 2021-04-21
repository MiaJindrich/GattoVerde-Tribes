package com.gattoverdetribes.gattoverdetribes.models.troops;

import static com.gattoverdetribes.gattoverdetribes.models.troops.TroopType.generateTroop;

import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidTroopException;

public class TroopFactory {

  public static Troop createTroop(String troopType) throws InvalidTroopException {
    for (TroopType type : TroopType.values()) {
      if (TroopType.valueOf(troopType.toUpperCase()).equals(type)) {
        return generateTroop(type);
      }
    }
    throw new InvalidTroopException("Created such troop can not be. Yrsssss.");
  }
}