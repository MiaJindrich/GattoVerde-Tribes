package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingFactory.buildBuilding;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingParameterException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NotEnoughResourcesException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.buildings.BuildingType;
import com.gattoverdetribes.gattoverdetribes.repositories.BuildingRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.services.BuildingService;
import com.gattoverdetribes.gattoverdetribes.services.KingdomService;
import java.util.Collections;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BuildingServiceTest {

  @Mock
  private BuildingRepository buildingRepository;
  @Autowired
  private BuildingService buildingService;
  @Autowired
  private KingdomRepository kingdomRepository;
  @Autowired
  private KingdomService kingdomService;

  private Kingdom kingdom;
  private Player player;

  @BeforeEach
  public void setup() {
    kingdom = kingdomService.createKingdom("rix's kingdom");
    player = new Player("rix12345", "rix12345", kingdom);
    kingdomRepository.save(kingdom);
  }


  @Test
  public void testSaveBuilding() {
    Building farm1 = buildBuilding("farm");
    when(buildingRepository.findAll()).thenReturn(Collections.singletonList(farm1));
    assertTrue(buildingRepository.findAll().contains(farm1));
    assertThat(farm1).extracting(Building::getType).isEqualTo(BuildingType.FARM);
  }

  @Test
  public void testCreateCorrectTypeBuilding() throws IllegalArgumentException {
    Building farm = buildBuilding("farm");
    Building academy = buildBuilding("academy");
    Building townhall = buildBuilding("townhall");
    Building mine = buildBuilding("mine");
    Assert.assertEquals(BuildingType.FARM, farm.getType());
    Assert.assertEquals(BuildingType.ACADEMY, academy.getType());
    Assert.assertEquals(BuildingType.TOWNHALL, townhall.getType());
    Assert.assertEquals(BuildingType.MINE, mine.getType());
  }

  @Test
  public void testCreateWrongTypeBuilding() throws InvalidBuildingException {
    assertThatExceptionOfType(InvalidBuildingException.class)
        .isThrownBy(() -> buildingService.createBuilding("frrm", kingdom))
        .withMessage("Created such building can not be. Yrsssss.");
  }

  @Test
  public void validateBuildingTypeTownhallTest() throws InvalidBuildingException {
    assertThatExceptionOfType(InvalidBuildingException.class)
        .isThrownBy(() -> buildingService.createBuilding("townhall", kingdom))
        .withMessage("Only one townhall kingdom can have. Yes, hrrrm.");
  }

  @Test
  public void validateNoBuildingTypeTest() {
    assertThatExceptionOfType(MissingParameterException.class)
        .isThrownBy(() -> buildingService.createBuilding("", kingdom))
        .withMessage("Fill in building type you must.");
  }

  @Test
  public void testCreateBuildingPass() {
    Building building = mock(Building.class);
    Kingdom kingdom = mock(Kingdom.class);
    doCallRealMethod().when(building).setLevel(any(Integer.class));
    doCallRealMethod().when(building).setKingdom(any(Kingdom.class));
    building.setKingdom(kingdom);
    building.setLevel(0);
    verify(building, times(1)).setLevel(0);
    verify(building, times(1)).setKingdom(kingdom);
  }

  public void purchaseBuildingTest() {
    int buildingsCountBefore = kingdom.getBuildings().size();
    buildingService.purchaseBuilding("mine", player);
    int buildingsCountAfter = kingdom.getBuildings().size();

    assertEquals(buildingsCountBefore + 1, buildingsCountAfter);
  }

  @Test
  public void upgradeBuildingTest() throws NotEnoughResourcesException {
    Building buildingBeforeUpgrading = buildingService.getBuilding(kingdom, "townhall");
    int buildingLevelBeforeUpgrading = buildingBeforeUpgrading.getLevel();
    buildingService.upgradeBuilding(player, buildingBeforeUpgrading.getId());
    Building buildingAfterUpgrading = buildingService.getBuilding(kingdom, "townhall");
    int buildingLevelAfterUpgrading = buildingAfterUpgrading.getLevel();

    assertEquals(buildingLevelBeforeUpgrading + 1, buildingLevelAfterUpgrading);
  }
}
