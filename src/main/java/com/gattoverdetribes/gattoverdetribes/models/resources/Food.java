package com.gattoverdetribes.gattoverdetribes.models.resources;

import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import javax.persistence.Entity;

@Entity
public class Food extends Resource {

  public Food() {
    this.amount = ExternalConfig.getInstance().getResourceStartFood();
    setType(ResourceType.FOOD);
  }

  public Food(Integer amount) {
    this.amount = amount;
  }

  public Food(Integer amount, Kingdom kingdom) {
    this.amount = amount;
    this.kingdom = kingdom;
  }
}
