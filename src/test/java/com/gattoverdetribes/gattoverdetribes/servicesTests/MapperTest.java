package com.gattoverdetribes.gattoverdetribes.servicesTests;

import com.gattoverdetribes.gattoverdetribes.dtos.BuildingDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.CreateBuildingResponseDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.KingdomDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.PlayerRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterResponseDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.ResourceDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.TroopDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.mappers.Mapper;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingType;
import com.gattoverdetribes.gattoverdetribes.models.buildings.TownHall;
import com.gattoverdetribes.gattoverdetribes.models.resources.Food;
import com.gattoverdetribes.gattoverdetribes.models.resources.Gold;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.models.troops.Troop;
import com.gattoverdetribes.gattoverdetribes.models.troops.TroopType;
import com.gattoverdetribes.gattoverdetribes.services.KingdomService;
import com.gattoverdetribes.gattoverdetribes.services.TroopService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MapperTest {
  @Autowired
  private Mapper mapper;
  @Autowired
  private KingdomService kingdomService;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private TroopService troopService;

  @Test
  public void buildingListToDtoTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    List<BuildingDetailsDTO> buildingDetailsDTOList = mapper.modelToDto(kingdom.getBuildings());
    Assertions.assertEquals(4, buildingDetailsDTOList.size());
  }

  @Test
  public void buildingDetailsDtoToModelTownhallTest() {
    BuildingDetailsDTO buildingDetailsDTO = new BuildingDetailsDTO();
    buildingDetailsDTO.setId(1L);
    buildingDetailsDTO.setType(BuildingType.TOWNHALL);
    buildingDetailsDTO.setLevel(1);
    buildingDetailsDTO.setHp(1);
    buildingDetailsDTO.setStartedAt(1609950129L);
    buildingDetailsDTO.setFinishedAt(1609950429L);
    Building building = mapper.dtoToModel(buildingDetailsDTO);
    Assertions.assertEquals(BuildingType.TOWNHALL, building.getType());
  }

  @Test
  public void buildingDetailsDtoToModelMineTest() {
    BuildingDetailsDTO buildingDetailsDTO = new BuildingDetailsDTO();
    buildingDetailsDTO.setId(1L);
    buildingDetailsDTO.setType(BuildingType.MINE);
    buildingDetailsDTO.setLevel(1);
    buildingDetailsDTO.setHp(1);
    buildingDetailsDTO.setStartedAt(1609950129L);
    buildingDetailsDTO.setFinishedAt(1609950429L);
    Building building = mapper.dtoToModel(buildingDetailsDTO);
    Assertions.assertEquals(BuildingType.MINE, building.getType());
  }

  @Test
  public void buildingToCreateBuildingResponseDtoTest() {
    TownHall townHall = new TownHall();
    townHall.setId(1L);
    townHall.setLevel(1);
    townHall.setHp(1);
    townHall.setStartedAt(1609950129L);
    townHall.setFinishedAt(1609950429L);
    CreateBuildingResponseDTO createBuildingResponseDTO =
        mapper.buildingToCreateBuildingResponseDto(townHall);
    Assertions.assertEquals("townhall", createBuildingResponseDTO.getType());
    Assertions.assertEquals(1, createBuildingResponseDTO.getHp());
  }

  @Test
  public void kingdomModelToDtoTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    KingdomDetailsDTO kingdomDetailsDTO = mapper.modelToDto(kingdom);
    Assertions.assertEquals(kingdom.getName(), kingdomDetailsDTO.getName());
    Assertions.assertEquals(kingdom.getId(), kingdomDetailsDTO.getId());
    Assertions.assertEquals(kingdom.getLocation().getXcord(), kingdomDetailsDTO.getXcord());
  }

  @Test
  public void listOfKingdomToListKingdomDetailsDTOTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    Kingdom kingdom1 = kingdomService.createKingdom("asd's kingdom");
    List<Kingdom> kingdoms = Arrays.asList(kingdom, kingdom1);
    List<KingdomDetailsDTO> kingdomDetailsDTOS = mapper.kingdomToKingdomDetailsDTO(kingdoms);
    Assertions.assertEquals(2, kingdomDetailsDTOS.size());
    Assertions.assertEquals(kingdom.getLocation().getXcord(), kingdomDetailsDTOS.get(0).getXcord());
  }

  @Test
  public void playerModelToDtoTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    Player player1 = new Player("asd", passwordEncoder.encode("asd12345"), kingdom);
    PlayerRequestDTO playerRequestDTO = mapper.modelToDto(player1);
    Assertions.assertEquals(player1.getKingdom().getName(), playerRequestDTO.getKingdomName());
    Assertions.assertEquals(player1.getPassword(), playerRequestDTO.getPassword());
  }

  @Test
  public void playerToRegisterResponseDTOTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    Player player1 = new Player("asd", passwordEncoder.encode("asd12345"), kingdom);
    RegisterResponseDTO registerResponseDTO = mapper.playerToRegisterResponseDTO(player1);
    Assertions.assertEquals(player1.getUsername(), registerResponseDTO.getUsername());
    Assertions.assertEquals(player1.getKingdom().getId(), registerResponseDTO.getKingdomId());
  }

  @Test
  public void resourceModelToDtoTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    Food food = new Food();
    food.setId(1L);
    food.setAmount(500);
    food.setKingdom(kingdom);
    ResourceDetailsDTO resourceDetailsDTO = mapper.modelToDto(food);
    Assertions.assertEquals(kingdom, resourceDetailsDTO.getKingdom());
    Assertions.assertEquals(food.getType().toString().toLowerCase(), resourceDetailsDTO.getType());
  }

  @Test
  public void resourceGoldModelToDtoTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    Gold gold = new Gold();
    gold.setId(1L);
    gold.setAmount(500);
    gold.setKingdom(kingdom);
    ResourceDetailsDTO resourceDetailsDTO = mapper.modelToDto(gold);
    Assertions.assertEquals(kingdom, resourceDetailsDTO.getKingdom());
    Assertions.assertEquals(gold.getType().toString().toLowerCase(), resourceDetailsDTO.getType());
  }

  @Test
  public void listOfResourceToListResourceDetailsDTOTest() {
    List<Resource> resources = kingdomService.createKingdom("rix's kingdom").getResources();
    List<ResourceDetailsDTO> resourceDetailsDTOS = mapper.resourceToResourceDetailsDTO(resources);
    Assertions.assertEquals(2, resourceDetailsDTOS.size());
    Assertions.assertEquals(
        resources.get(0).getType().toString().toLowerCase(), resourceDetailsDTOS.get(0).getType());
  }

  @Test
  public void troopModelToTroopDetailsDtoTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    Troop troop = troopService.createTroop(TroopType.ARCHER.toString(), kingdom);
    TroopDetailsDTO troopDetailsDTO = mapper.modelToDto(troop);
    Assertions.assertEquals(troop.getAttack(), troopDetailsDTO.getAttack());
    Assertions.assertEquals(troop.getLevel(), troopDetailsDTO.getLevel());
  }

  @Test
  public void troopHorsemanModelToTroopDetailsDtoTest() {
    Kingdom kingdom = kingdomService.createKingdom("rix's kingdom");
    Troop troop = troopService.createTroop(TroopType.HORSEMAN.toString(), kingdom);
    TroopDetailsDTO troopDetailsDTO = mapper.modelToDto(troop);
    Assertions.assertEquals(troop.getAttack(), troopDetailsDTO.getAttack());
    Assertions.assertEquals(troop.getLevel(), troopDetailsDTO.getLevel());
  }
}
