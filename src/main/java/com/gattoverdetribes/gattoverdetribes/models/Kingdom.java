package com.gattoverdetribes.gattoverdetribes.models;

import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.models.troops.Troop;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "kingdoms")
public class Kingdom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int kingdomFoodProduction = 0;
  private int kingdomGoldProduction = 0;
  private int maxStorage = 0;

  @OneToOne(mappedBy = "kingdom", cascade = CascadeType.ALL)
  private Location location;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "kingdom")
  private List<Resource> resources;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "kingdom")
  private List<Building> buildings;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "kingdom")
  private List<Troop> troops;

  @OneToOne(mappedBy = "kingdom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Player player;

  public Kingdom(String name, Player player) {
    this.name = name;
    this.player = player;
  }

  public Kingdom(Player player) {
    this.player = player;
  }

  public Kingdom(String kingdomName) {
    this.name = kingdomName;
  }

  public Kingdom() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public List<Resource> getResources() {
    return resources;
  }

  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }

  public List<Building> getBuildings() {
    return buildings;
  }

  public void setBuildings(List<Building> buildings) {
    this.buildings = buildings;
  }

  public List<Troop> getTroops() {
    return troops;
  }

  public void setTroops(List<Troop> troops) {
    this.troops = troops;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public int getKingdomFoodProduction() {
    return kingdomFoodProduction;
  }

  public void setKingdomFoodProduction(int foodProduction) {
    this.kingdomFoodProduction = foodProduction;
  }

  public int getKingdomGoldProduction() {
    return kingdomGoldProduction;
  }

  public void setKingdomGoldProduction(int goldProduction) {
    this.kingdomGoldProduction = goldProduction;
  }

  public int getMaxStorage() {
    return maxStorage;
  }

  public void setMaxStorage(int maxStorage) {
    this.maxStorage = maxStorage;
  }
}