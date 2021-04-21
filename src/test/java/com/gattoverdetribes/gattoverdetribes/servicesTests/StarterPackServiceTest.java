package com.gattoverdetribes.gattoverdetribes.servicesTests;

import com.gattoverdetribes.gattoverdetribes.GattoverdeTribesApplication;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.services.BuildingService;
import com.gattoverdetribes.gattoverdetribes.services.KingdomService;
import com.gattoverdetribes.gattoverdetribes.services.ResourceService;
import com.gattoverdetribes.gattoverdetribes.services.StarterPackService;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GattoverdeTribesApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class StarterPackServiceTest {

  @Autowired
  KingdomRepository kingdomRepository;
  @Autowired
  StarterPackService starterPackService;
  @Autowired
  ResourceService resourceService;
  @Autowired
  BuildingService buildingService;
  @Autowired
  KingdomService kingdomService;

  Kingdom kingdom;

  @BeforeEach
  public void setup() {
    kingdom = kingdomService.createKingdom("Narnia");
    kingdomRepository.save(kingdom);
  }

  @Test
  public void generateKingdomLocationTest() {
    Assert.assertNotNull(kingdom.getLocation());
  }

  @Test
  public void setStartingResourcesTest() {
    List<Resource> actualResources = kingdom.getResources();
    Integer actualAmount = kingdom.getResources().get(0).getAmount();
    Integer expectedAmount = 500;

    Assert.assertEquals(2, actualResources.size());
    Assert.assertEquals(expectedAmount, actualAmount);
  }

  @Test
  public void setStartingBuildingsTest() {
    List<Building> actualBuildings = kingdom.getBuildings();
    Building townhall = kingdom.getBuildings().stream().filter(b -> b.getType().name()
        .equals("TOWNHALL")).findAny().get();
    Integer expectedLevel = 1;

    Assert.assertEquals(4, actualBuildings.size());
    Assert.assertEquals(expectedLevel, townhall.getLevel());
  }
}
