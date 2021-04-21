package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.dtos.KingdomDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.mappers.Mapper;
import com.gattoverdetribes.gattoverdetribes.exceptions.NoContentException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KingdomServiceImpl implements KingdomService {

  private final KingdomRepository kingdomRepository;
  private final StarterPackService starterPackService;
  private final Mapper mapper;

  @Autowired
  public KingdomServiceImpl(
      KingdomRepository kingdomRepository,
      StarterPackServiceImpl starterPackService,
      Mapper mapper) {
    this.kingdomRepository = kingdomRepository;
    this.starterPackService = starterPackService;
    this.mapper = mapper;
  }

  @Override
  public List<KingdomDetailsDTO> getKingdoms() {
    List<Kingdom> kingdoms = kingdomRepository.findAll();
    if (kingdoms.isEmpty()) {
      throw new NoContentException("No kingdoms in this world there are.");
    }
    return mapper.kingdomToKingdomDetailsDTO(kingdoms);
  }

  @Override
  public Kingdom getById(Long id) {
    var optionalKingdom = kingdomRepository.findById(id);
    return optionalKingdom.orElse(null);
  }

  @Override
  public Kingdom getKingdomByPlayerId(Long id) {
    return kingdomRepository.findByPlayerId(id);
  }

  @Override
  public void saveKingdom(Kingdom kingdom) {
    kingdomRepository.save(kingdom);
  }

  @Override
  public Kingdom createKingdom(String kingdomName) {
    Kingdom kingdom = new Kingdom(kingdomName);
    saveKingdom(kingdom);
    kingdom.setLocation(starterPackService.generateKingdomLocation(kingdom));
    kingdom.setBuildings(starterPackService.setStartingBuildings(kingdom));
    kingdom.setResources(starterPackService.setStartingResources(kingdom));
    return kingdom;
  }
}
