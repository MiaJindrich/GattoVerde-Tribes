package com.gattoverdetribes.gattoverdetribes.models.buildings;

import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "buildings")
public abstract class Building {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer level;
  private Integer hp;

  @Transient
  @Enumerated(EnumType.STRING)
  private BuildingType type;

  private Long startedAt;
  private Long finishedAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "kingdomId", referencedColumnName = "id")
  private Kingdom kingdom;

  public Building() {
  }

  public void fillDefaults(String type) {
    ExternalConfig config = ExternalConfig.getInstance();
    this.setLevel(config.getBuildingStartLevel());
    this.setHp(config.getBuildingStartHp().getOrDefault(type, 1000));
    this.setStartedAt(System.currentTimeMillis() / 1000);
    this.setFinishedAt(this.getStartedAt() + config.getBuildingConstructionTime()
        .getOrDefault(type, 100));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getHp() {
    return hp;
  }

  public void setHp(Integer hp) {
    this.hp = hp;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getLevel() {
    return level;
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

  public BuildingType getType() {
    return type;
  }

  public void setType(BuildingType type) {
    this.type = type;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }

  public int getGoldProduction() {
    if (level > 1) {
      return (int) (level * 10 * 1.1);
    }
    return level * 10;
  }

  public int getFoodProduction() {
    if (level > 1) {
      return (int) (level * 10 * 1.1);
    }
    return level * 10;
  }
}