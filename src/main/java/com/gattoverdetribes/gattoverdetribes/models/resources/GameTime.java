package com.gattoverdetribes.gattoverdetribes.models.resources;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_time")
public class GameTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long ticks;
  private long serverStart;

  public GameTime() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getTicks() {
    return ticks;
  }

  public void setTicks(long ticks) {
    this.ticks = ticks;
  }

  public long getServerStart() {
    return serverStart;
  }

  public void setServerStart(long serverStart) {
    this.serverStart = serverStart;
  }
}