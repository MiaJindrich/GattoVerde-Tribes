package com.gattoverdetribes.gattoverdetribes;

import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Scope("singleton")
@Configuration
@PropertySource("file:config/tribesconfig.properties")
public class ExternalConfig {

  private static ExternalConfig INSTANCE;

  @PostConstruct
  @Bean
  public void initialize() {
    INSTANCE = this;
  }

  public static ExternalConfig getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ExternalConfig();
    }
    return INSTANCE;
  }


  @Value("#{${building.construction.time:{'farm': 500, 'mine': 500, 'academy': 800}}}")
  private Map<String, Integer> buildingConstructionTime;

  @Value("${building.construction.cost:100}")
  private Integer buildingConstructionCost;

  @Value("${building.upgrade.cost:100}")
  private Integer buildingUpgradeCost;

  @Value("${building.max.level:20}")
  private Integer buildingMaxLevel;

  @Value("#{${building.start.hp:{'farm': 500, 'mine': 500, 'townhall': 1000, 'academy': 800}}}")
  private Map<String, Integer> buildingStartHp;

  @Value("${building.storage:1000}")
  private Integer buildingStorage;

  @Value("${building.food.generation:10}")
  private Integer buildingFoodGeneration;

  @Value("${building.gold.generation:10}")
  private Integer buildingGoldGeneration;

  @Value("#{${troop.training.time:{'troop': 60, 'archer': 90, 'swordsman': 60, 'horseman': 120}}}")
  private Map<String, Integer> troopTrainingTime;

  @Value("${troop.food.usage:1}")
  private Integer troopFoodUsage;

  @Value("${max.queue.length:5}")
  private Integer maxQueueLength;

  @Value("${troop.max.level:20}")
  private Integer troopMaxLevel;

  @Value("${resource.start.gold:500}")
  private Integer resourceStartGold;

  @Value("${resource.start.food:500}")
  private Integer resourceStartFood;

  @Value("${building.start.level:1}")
  private Integer buildingStartLevel;

  @Value("${troop.start.level:1}")
  private Integer troopStartLevel;

  @Value("#{${troop.training.cost:{'troop': 5, 'archer': 20, 'swordsman': 10, 'horseman': 30}}}")
  private Map<String, Integer> troopTrainingCost;

  @Value("#{${troop.start.hp:{'troop': 100, 'archer': 70, 'swordsman': 100, 'horseman': 130}}}")
  private Map<String, Integer> troopStartHp;

  @Value("#{${troop.start.attack:{'troop': 50, 'archer': 70, 'swordsman': 50, 'horseman': 80}}}")
  private Map<String, Integer> troopStartAttack;

  @Value("#{${troop.start.defense:{'troop': 50, 'archer': 20, 'swordsman': 50, 'horseman': 70}}}")
  private Map<String, Integer> troopStartDefense;

  public Map<String, Integer> getBuildingStartHp() {
    return buildingStartHp;
  }

  public void setBuildingStartHp(Map<String, Integer> buildingStartHp) {
    this.buildingStartHp = buildingStartHp;
  }

  public Map<String, Integer> getBuildingConstructionTime() {
    return buildingConstructionTime;
  }

  public void setBuildingConstructionTime(
      Map<String, Integer> buildingConstructionTime) {
    this.buildingConstructionTime = buildingConstructionTime;
  }

  public static void setINSTANCE(ExternalConfig instance) {
    ExternalConfig.INSTANCE = instance;
  }

  public Integer getBuildingConstructionCost() {
    if (buildingConstructionCost == null) {
      return 100;
    }
    return buildingConstructionCost;
  }

  public void setBuildingConstructionCost(Integer buildingConstructionCost) {
    this.buildingConstructionCost = buildingConstructionCost;
  }

  public Integer getBuildingUpgradeCost() {
    if (buildingUpgradeCost == null) {
      return 100;
    }
    return buildingUpgradeCost;
  }

  public void setBuildingUpgradeCost(Integer buildingUpgradeCost) {
    this.buildingUpgradeCost = buildingUpgradeCost;
  }

  public Integer getBuildingMaxLevel() {
    if (buildingMaxLevel == null) {
      return 20;
    }
    return buildingMaxLevel;
  }

  public void setBuildingMaxLevel(Integer buildingMaxLevel) {
    this.buildingMaxLevel = buildingMaxLevel;
  }

  public Integer getBuildingStorage() {
    if (buildingStorage == null) {
      return 1000;
    }
    return buildingStorage;
  }

  public void setBuildingStorage(Integer buildingStorage) {
    this.buildingStorage = buildingStorage;
  }

  public Integer getBuildingFoodGeneration() {
    if (buildingFoodGeneration == null) {
      return 10;
    }
    return buildingFoodGeneration;
  }

  public void setBuildingFoodGeneration(Integer buildingFoodGeneration) {
    this.buildingFoodGeneration = buildingFoodGeneration;
  }

  public Integer getBuildingGoldGeneration() {
    if (buildingGoldGeneration == null) {
      return 10;
    }
    return buildingGoldGeneration;
  }

  public void setBuildingGoldGeneration(Integer buildingGoldGeneration) {
    this.buildingGoldGeneration = buildingGoldGeneration;
  }

  public Map<String, Integer> getTroopTrainingTime() {
    return troopTrainingTime;
  }

  public void setTroopTrainingTime(Map<String, Integer> troopTrainingTime) {
    this.troopTrainingTime = troopTrainingTime;
  }

  public Integer getTroopFoodUsage() {
    if (troopFoodUsage == null) {
      return 1;
    }
    return troopFoodUsage;
  }

  public void setTroopFoodUsage(Integer troopFoodUsage) {
    this.troopFoodUsage = troopFoodUsage;
  }

  public Integer getMaxQueueLength() {
    if (maxQueueLength == null) {
      return 5;
    }
    return maxQueueLength;
  }

  public void setMaxQueueLength(Integer maxQueueLength) {
    this.maxQueueLength = maxQueueLength;
  }

  public Integer getTroopMaxLevel() {
    if (troopMaxLevel == null) {
      return 20;
    }
    return troopMaxLevel;
  }

  public void setTroopMaxLevel(Integer troopMaxLevel) {
    this.troopMaxLevel = troopMaxLevel;
  }

  public Integer getResourceStartGold() {
    if (resourceStartGold == null) {
      return 500;
    }
    return resourceStartGold;
  }

  public void setResourceStartGold(Integer resourceStartGold) {
    this.resourceStartGold = resourceStartGold;
  }

  public Integer getResourceStartFood() {
    if (resourceStartFood == null) {
      return 500;
    }
    return resourceStartFood;
  }

  public void setResourceStartFood(Integer resourceStartFood) {
    this.resourceStartFood = resourceStartFood;
  }

  public Integer getBuildingStartLevel() {
    if (buildingStartLevel == null) {
      return 1;
    }
    return buildingStartLevel;
  }

  public void setBuildingStartLevel(Integer buildingStartLevel) {
    this.buildingStartLevel = buildingStartLevel;
  }

  public Integer getTroopStartLevel() {
    if (troopStartLevel == null) {
      return 1;
    }
    return troopStartLevel;
  }

  public void setTroopStartLevel(Integer troopStartLevel) {
    this.troopStartLevel = troopStartLevel;
  }

  public Map<String, Integer> getTroopStartHp() {
    return troopStartHp;
  }

  public void setTroopStartHp(Map<String, Integer> troopStartHp) {
    this.troopStartHp = troopStartHp;
  }

  public Map<String, Integer> getTroopTrainingCost() {
    return troopTrainingCost;
  }

  public void setTroopTrainingCost(Map<String, Integer> troopTrainingCost) {
    this.troopTrainingCost = troopTrainingCost;
  }

  public Map<String, Integer> getTroopStartAttack() {
    return troopStartAttack;
  }

  public void setTroopStartAttack(Map<String, Integer> troopStartAttack) {
    this.troopStartAttack = troopStartAttack;
  }

  public Map<String, Integer> getTroopStartDefense() {
    return troopStartDefense;
  }

  public void setTroopStartDefense(Map<String, Integer> troopStartDefense) {
    this.troopStartDefense = troopStartDefense;
  }
}

