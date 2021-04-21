package com.gattoverdetribes.gattoverdetribes.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int xcord;
  private int ycord;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Kingdom kingdom;

  public Location() {
  }

  public Location(int xcord, int ycord) {
    this.xcord = xcord;
    this.ycord = ycord;
  }

  public int getXcord() {
    return xcord;
  }

  public void setXcord(int xcord) {
    this.xcord = xcord;
  }

  public int getYcord() {
    return ycord;
  }

  public void setYcord(int ycord) {
    this.ycord = ycord;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }
}

