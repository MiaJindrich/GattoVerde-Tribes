package com.gattoverdetribes.gattoverdetribes.mappers;

import com.gattoverdetribes.gattoverdetribes.dtos.BuildingDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.CreateBuildingResponseDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.KingdomDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.PlayerRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterResponseDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.ResourceDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.TroopDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Academy;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingType;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Farm;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Mine;
import com.gattoverdetribes.gattoverdetribes.models.buildings.TownHall;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.models.troops.Troop;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

  private final ModelMapper mapper;

  public Mapper() {
    mapper = new ModelMapper();
  }

  public Building dtoToModel(BuildingDetailsDTO buildingDetailsDTO) {
    if (buildingDetailsDTO.getType() == BuildingType.TOWNHALL) {
      return mapper.map(buildingDetailsDTO, TownHall.class);
    } else if (buildingDetailsDTO.getType() == BuildingType.ACADEMY) {
      return mapper.map(buildingDetailsDTO, Academy.class);
    } else if (buildingDetailsDTO.getType() == BuildingType.FARM) {
      return mapper.map(buildingDetailsDTO, Farm.class);
    } else {
      return mapper.map(buildingDetailsDTO, Mine.class);
    }
  }

  public CreateBuildingResponseDTO buildingToCreateBuildingResponseDto(Building building) {
    CreateBuildingResponseDTO createBuildingResponseDTO =
        mapper.map(building, CreateBuildingResponseDTO.class);
    createBuildingResponseDTO.setType(building.getType().toString().toLowerCase());
    return createBuildingResponseDTO;
  }

  public List<KingdomDetailsDTO> kingdomToKingdomDetailsDTO(List<Kingdom> kingdoms) {
    return kingdoms.stream().map(this::modelToDto).collect(Collectors.toList());
  }

  public RegisterResponseDTO playerToRegisterResponseDTO(Player player) {
    RegisterResponseDTO registerResponseDTO = mapper.map(player, RegisterResponseDTO.class);
    registerResponseDTO.setKingdomId(player.getKingdom().getId());
    return registerResponseDTO;
  }

  public List<ResourceDetailsDTO> resourceToResourceDetailsDTO(List<Resource> resources) {
    return resources.stream().map(this::modelToDto).collect(Collectors.toList());
  }

  public TroopDetailsDTO modelToDto(Troop troop) {
    return mapper.map(troop, TroopDetailsDTO.class);
  }

  public ResourceDetailsDTO modelToDto(Resource resource) {
    ResourceDetailsDTO resourceDetailsDTO = mapper.map(resource, ResourceDetailsDTO.class);
    resourceDetailsDTO.setType(resourceDetailsDTO.getType().toLowerCase());
    resourceDetailsDTO.setGeneration(0L);
    return resourceDetailsDTO;
  }

  public PlayerRequestDTO modelToDto(Player player) {
    PlayerRequestDTO playerRequestDTO = mapper.map(player, PlayerRequestDTO.class);
    playerRequestDTO.setKingdomName(player.getKingdom().getName());
    return playerRequestDTO;
  }

  public KingdomDetailsDTO modelToDto(Kingdom kingdom) {
    KingdomDetailsDTO kingdomDetailsDTO = mapper.map(kingdom, KingdomDetailsDTO.class);
    kingdomDetailsDTO.setXcord(kingdom.getLocation().getXcord());
    kingdomDetailsDTO.setYcord(kingdom.getLocation().getYcord());
    return kingdomDetailsDTO;
  }

  public BuildingDetailsDTO modelToDto(Building building) {
    return mapper.map(building, BuildingDetailsDTO.class);
  }

  public List<BuildingDetailsDTO> modelToDto(List<Building> buildings) {
    return buildings.stream().map(this::modelToDto).collect(Collectors.toList());
  }

  public List<TroopDetailsDTO> modelToListTroopDetailsDto(List<Troop> troops) {
    return troops.stream().map(this::modelToDto).collect(Collectors.toList());
  }
}
