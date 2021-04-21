package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.gattoverdetribes.gattoverdetribes.dtos.ResourceDetailsDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidBuildingException;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidResourceException;
import com.gattoverdetribes.gattoverdetribes.exceptions.NotEnoughResourcesException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.resources.Food;
import com.gattoverdetribes.gattoverdetribes.models.resources.Gold;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.models.resources.ResourceFactory;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.ResourceRepository;
import com.gattoverdetribes.gattoverdetribes.services.KingdomService;
import com.gattoverdetribes.gattoverdetribes.services.ResourceService;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
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
public class ResourceServiceTest {

  @Autowired
  private ResourceService resourceService;
  @Autowired
  private ResourceRepository resourceRepository;
  @Autowired
  private KingdomRepository kingdomRepository;
  @Autowired
  private KingdomService kingdomService;

  Kingdom kingdom;

  @BeforeEach
  public void setup() {
    kingdom = kingdomService.createKingdom("Narnia");
  }

  @Test
  public void findAllResourcesIntegrationTest() {
    List<ResourceDetailsDTO> actualList = resourceService.getResourcesByKingdom(kingdom);
    Assert.assertEquals(2, actualList.size());
  }

  @Test
  public void createResource() {
    Resource food = ResourceFactory.createResource("food");
    Resource gold = ResourceFactory.createResource("gold");

    Assert.assertEquals(Food.class, food.getClass());
    Assert.assertEquals(Gold.class, gold.getClass());
  }

  @Test
  public void createResourceThrowsExceptionTest() throws InvalidResourceException {
    assertThatExceptionOfType(InvalidBuildingException.class)
        .isThrownBy(() -> resourceService.createResource("wood", kingdom))
        .withMessage("Created such resource can not be. Yrsssss.");
  }

  @Test
  public void checkSufficientResourcesTest() {
    Assertions
        .assertDoesNotThrow(() -> resourceService.buildBuildingCheckSufficientResources(kingdom));
  }

  @Test
  public void checkInsufficientResourcesTest() {
    Gold gold = resourceRepository.findByKingdom(kingdom);
    gold.setAmount(0);
    resourceService.saveResource(gold);
    Assertions.assertThrows(NotEnoughResourcesException.class, () -> {
      resourceService.buildBuildingCheckSufficientResources(kingdom);
    });
  }

  @Test
  void calculateProductionTest() {
    Optional<Kingdom> kingdomOptBefore = kingdomRepository.findById(kingdom.getId());
    int initial = kingdomOptBefore.get().getResources().get(0).getAmount();
    resourceService.calculateKingdomsProduction();
    Optional<Kingdom> kingdomOptAfter = kingdomRepository.findById(kingdom.getId());
    Assert.assertEquals(Optional.of(initial + kingdomOptAfter.get().getKingdomFoodProduction()),
        Optional.ofNullable(kingdomOptAfter.get().getResources().get(0).getAmount()));
    Assert.assertEquals(20, kingdomOptAfter.get().getKingdomFoodProduction());
    Assert.assertEquals(20, kingdomOptAfter.get().getKingdomGoldProduction());
  }
}