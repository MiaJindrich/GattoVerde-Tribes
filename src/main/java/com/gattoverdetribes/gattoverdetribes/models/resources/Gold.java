package com.gattoverdetribes.gattoverdetribes.models.resources;

import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import javax.persistence.Entity;

@Entity
public class Gold extends Resource {

  public Gold() {
    this.amount = ExternalConfig.getInstance().getResourceStartGold();
    setType(ResourceType.GOLD);
  }

  public Gold(Integer amount) {
    this.amount = amount;
  }

  public Gold(Integer amount, Kingdom kingdom) {
    this.amount = amount;
    this.kingdom = kingdom;
  }
}
