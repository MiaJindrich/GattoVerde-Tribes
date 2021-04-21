package com.gattoverdetribes.gattoverdetribes.dtos;

import com.gattoverdetribes.gattoverdetribes.models.troops.TroopType;

public class TroopDetailsDTO {

  private Long id;
  private TroopType type;
  private int level;
  private int hp;
  private int attack;
  private int defense;
  private Long startedAt;
  private Long finishedAt;

  public TroopDetailsDTO() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TroopType getType() {
    return type;
  }

  public void setType(TroopType type) {
    this.type = type;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public int getDefense() {
    return defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public Long getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(Long startedAt) {
    this.startedAt = startedAt;
  }

  public Long getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(Long finishedAt) {
    this.finishedAt = finishedAt;
  }
}
