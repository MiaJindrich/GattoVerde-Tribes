package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.dtos.KingdomDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import java.util.List;

public interface KingdomService {

  List<KingdomDetailsDTO> getKingdoms();

  Kingdom getById(Long id);

  Kingdom getKingdomByPlayerId(Long id);

  void saveKingdom(Kingdom kingdom);

  Kingdom createKingdom(String kingdomName);
}
