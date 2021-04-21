package com.gattoverdetribes.gattoverdetribes.models.troops;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "troops")
public abstract class Troop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int level;
  private int hp;
  private int attack;
  private int defense;
  private Long startedAt;
  private Long finishedAt;

  @Transient
  @Enumerated(EnumType.STRING)
  private TroopType type;

  @ManyToOne
  @JoinColumn(name = "kingdomId", referencedColumnName = "id")
  @JsonIgnore
  private Kingdom kingdom;

  public Troop() {
  }

  public void fillDefaults(String type) {
    ExternalConfig config = ExternalConfig.getInstance();
    this.setLevel(config.getTroopStartLevel());
    this.setHp(config.getTroopStartHp().getOrDefault(type, 100));
    this.setAttack(config.getTroopStartAttack().getOrDefault(type, 50));
    this.setDefense(config.getTroopStartDefense().getOrDefault(type, 50));
    this.setStartedAt(System.currentTimeMillis() / 1000);
    this.setFinishedAt(this.getStartedAt() + config.getTroopTrainingTime()
        .getOrDefault(type, 60));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public TroopType getType() {
    return type;
  }

  public void setType(TroopType type) {
    this.type = type;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Troop troop = (Troop) o;
    return Objects.equals(id, troop.id) && type == troop.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type);
  }
}

