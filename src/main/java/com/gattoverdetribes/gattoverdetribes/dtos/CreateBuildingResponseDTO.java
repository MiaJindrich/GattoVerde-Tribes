package com.gattoverdetribes.gattoverdetribes.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;

@JsonInclude(Include.NON_NULL)
public class CreateBuildingResponseDTO {

  private Long id;
  private String type;
  private Integer level;
  private Integer hp;
  private Long startedAt;
  private Long finishedAt;

  @JsonIgnore private Kingdom kingdom;

  public CreateBuildingResponseDTO() {}

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getHp() {
    return hp;
  }

  public void setHp(Integer hp) {
    this.hp = hp;
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
