package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static org.junit.Assert.assertNotEquals;

import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingException;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingLevelException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.repositories.BuildingRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.services.BuildingService;
import com.gattoverdetribes.gattoverdetribes.services.KingdomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BuildingServiceIntegrationTest {

  @Autowired
  BuildingRepository buildingRepository;
  @Autowired
  BuildingService buildingService;
  @Autowired
  KingdomRepository kingdomRepository;
  @Autowired
  KingdomService kingdomService;

  Kingdom kingdom;
  Player player;

  @BeforeEach
  public void setUp() throws InvalidBuildingException {
    kingdom = kingdomService.createKingdom("Narnia");
    player = new Player("rix", "password12345");
    player.setKingdom(kingdom);
  }

  @Test
  public void upgradeLevelTest_FarmLevelIsLowerThanTownHalls() {
    Building farm = buildingService.getBuilding(kingdom, "farm");
    Integer levelBeforeUpgrade = farm.getLevel();
    buildingService.upgradeLevel(farm);
    buildingRepository.save(farm);
    Integer levelAfterUpgrade = farm.getLevel();

    assertNotEquals(levelBeforeUpgrade, levelAfterUpgrade);
  }

  @Test
  public void upgradeLevelTest_FarmLevelIsTheSameAsTownHalls() {
    Building farm = buildingService.getBuilding(kingdom, "farm");

    Assertions.assertThrows(InvalidBuildingLevelException.class, () -> {
      buildingService.upgradeBuilding(player, farm.getId());
    });
  }

  @Test
  public void upgradeLevelTest_upgradeTownHallLevel() {
    Building townhall = buildingService.getBuilding(kingdom, "townhall");
    Integer levelBeforeUpgrade = townhall.getLevel();
    buildingService.upgradeLevel(townhall);
    buildingRepository.save(townhall);
    Integer levelAfterUpgrade = townhall.getLevel();

    assertNotEquals(levelBeforeUpgrade, levelAfterUpgrade);
  }

  @Test
  public void checkMineLevelToTownHallOkTest() {
    Building townhall = buildingService.getBuilding(kingdom, "townhall");
    townhall.setLevel(2);
    buildingService.saveBuilding(townhall);
    Building mine = buildingService.getBuilding(kingdom, "mine");
    Assertions.assertDoesNotThrow(() -> buildingService.isBuildingUpgradeable(mine));
  }

  @Test
  public void checkFarmLevelToTownHallFailedTest() {
    Building farm = buildingService.getBuilding(kingdom, "farm");
    farm.setLevel(2);
    buildingRepository.save(farm);

    Assertions.assertThrows(InvalidBuildingLevelException.class, () -> {
      buildingService.isBuildingUpgradeable(farm);
    });
  }

  @Test
  public void checkTownHallLevelOkTest() {
    Building townhall = buildingService.getBuilding(kingdom, "townhall");
    Assertions.assertDoesNotThrow(() -> buildingService.isBuildingUpgradeable(townhall));
  }

  @Test
  public void checkTownHallLevelFailedTest() {
    Building townhall = buildingService.getBuilding(kingdom, "townhall");
    townhall.setLevel(20);
    buildingRepository.save(townhall);

    Assertions.assertThrows(InvalidBuildingLevelException.class, () -> {
      buildingService.isBuildingUpgradeable(townhall);
    });
  }
}
