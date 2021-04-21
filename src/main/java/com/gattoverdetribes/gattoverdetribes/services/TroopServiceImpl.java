package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.dtos.TroopDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.IdNotFoundException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NoContentException;
import com.gattoverdetribes.gattoverdetribes.mappers.Mapper;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidTroopException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingParameterException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.troops.Troop;
import com.gattoverdetribes.gattoverdetribes.models.troops.TroopFactory;
import com.gattoverdetribes.gattoverdetribes.models.troops.TroopType;
import com.gattoverdetribes.gattoverdetribes.repositories.TroopRepository;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TroopServiceImpl implements TroopService {

  private final TroopRepository troopRepository;
  private final Mapper mapper;

  public TroopServiceImpl(TroopRepository troopRepository, Mapper mapper) {
    this.troopRepository = troopRepository;
    this.mapper = mapper;
  }

  @Override
  public Troop createTroop(String troopType, Kingdom kingdom) {
    validateTroopType(troopType);
    Troop troop = TroopFactory.createTroop(troopType);
    troop.setKingdom(kingdom);
    saveTroop(troop);
    return troop;
  }

  @Override
  public void validateTroopType(String type) {
    if (type == null || type.isEmpty()) {
      throw new MissingParameterException("Fill in troop type you must.");
    }

    List<TroopType> troopTypes = Arrays.asList(TroopType.values());
    List<String> troops = troopTypes.stream().map(t -> t.toString().toLowerCase())
        .collect(Collectors.toList());
    boolean containsType = troops.contains(type.toLowerCase());

    if (!containsType) {
      throw new InvalidTroopException("Created such troop can not be. Yrsssss.");
    }
  }

  @Override
  public void saveTroop(Troop troop) {
    troopRepository.save(troop);
  }

  @Override
  public Troop getTroopById(Long id) {
    return troopRepository.findById(id).orElseThrow((IdNotFoundException::new));
  }

  @Override
  public List<TroopDetailsDTO> getTroopsByKingdom(Kingdom kingdom) {
    List<Troop> troops = troopRepository.findAll();
    if (troops.isEmpty()) {
      throw new NoContentException("No troops in this kingdom there are.");
    }
    return mapper.modelToListTroopDetailsDto(troops);
  }
}
