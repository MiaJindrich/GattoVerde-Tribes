package com.gattoverdetribes.gattoverdetribes.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;

public class ResourceDetailsDTO {

  private String type;
  private Integer amount;
  private Long generation;
  @JsonIgnore private Kingdom kingdom;

  public ResourceDetailsDTO() {}

  public ResourceDetailsDTO(String type, Integer amount, Long generation, Kingdom kingdom) {
    this.type = type;
    this.amount = amount;
    this.generation = generation;
    this.kingdom = kingdom;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Long getGeneration() {
    return generation;
  }

  public void setGeneration(Long generation) {
    this.generation = generation;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }
}
