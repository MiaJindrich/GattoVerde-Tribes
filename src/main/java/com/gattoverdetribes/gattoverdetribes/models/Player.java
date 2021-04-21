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
@Table(name = "players")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  private String avatar;
  private Integer points;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Kingdom kingdom;

  public Player() {}

  public Player(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Player(String username, String password, Kingdom kingdom) {
    this.username = username;
    this.password = password;
    this.kingdom = kingdom;
    this.avatar = "https://gravatar.com/avatar/12cbdd46acdcfbed7700d433d3b5a500?s=400&d=mp&r=x";
    this.points = 0;
  }

  public Player(String username, String password, String avatar, Integer points, Kingdom kingdom) {
    this.username = username;
    this.password = password;
    this.avatar = avatar;
    this.points = points;
    this.kingdom = kingdom;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }
}
