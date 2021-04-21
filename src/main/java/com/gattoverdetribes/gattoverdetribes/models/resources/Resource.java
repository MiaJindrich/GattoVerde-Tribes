package com.gattoverdetribes.gattoverdetribes.models.resources;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "resources")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public abstract class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;
  protected Integer amount;
  @ManyToOne(fetch = FetchType.LAZY)
  protected Kingdom kingdom;

  @Transient
  @Enumerated(EnumType.STRING)
  protected ResourceType type;

  public Resource() {
    this.amount = 0;
  }

  public Long getId() {
    return id;
  }

  public ResourceType getType() {
    return type;
  }

  public void setType(ResourceType type) {
    this.type = type;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }
}
