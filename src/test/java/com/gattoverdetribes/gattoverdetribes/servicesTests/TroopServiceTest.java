package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static com.gattoverdetribes.gattoverdetribes.models.troops.TroopFactory.createTroop;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidTroopException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.troops.Troop;
import com.gattoverdetribes.gattoverdetribes.models.troops.TroopType;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.TroopRepository;
import com.gattoverdetribes.gattoverdetribes.services.TroopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureWebMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TroopServiceTest {

  @Autowired
  TroopRepository troopRepository;
  @Autowired
  KingdomRepository kingdomRepository;
  @Autowired
  TroopService troopService;

  Troop archer;
  Troop horseman;
  Troop swordsman;
  Kingdom kingdom;

  @BeforeEach
  public void setUp() throws InvalidTroopException {
    kingdom = new Kingdom();
    kingdom.setId(1L);
    kingdom.setName("testKingdom");
    kingdomRepository.save(kingdom);
    archer = createTroop("archer");
    troopRepository.save(archer);
    horseman = createTroop("horseman");
    troopRepository.save(horseman);
    swordsman = createTroop("swordsman");
    troopRepository.save(swordsman);
  }

  @Test
  public void troopFoundByIdTest() {
    Troop actualTroop = troopService.getTroopById(archer.getId());
    Assertions.assertEquals(archer.getId(), actualTroop.getId());
  }

  @Test
  public void testCreateTroopForKingdom() {
    Troop archer2 = troopService.createTroop(archer.getType().toString(), kingdom);
    Assertions.assertTrue(troopRepository.findAll().contains(archer2));
    assertThat(archer2).extracting(Troop::getType).isEqualTo(TroopType.ARCHER);
  }

  @Test
  public void testCreateCorrectTroopType() throws IllegalArgumentException {
    Assertions.assertEquals(TroopType.ARCHER, archer.getType());
    Assertions.assertEquals(TroopType.HORSEMAN, horseman.getType());
    Assertions.assertEquals(TroopType.SWORDSMAN, swordsman.getType());
  }

  @Test
  public void testCreateWrongTroopType() throws InvalidTroopException {
    assertThatExceptionOfType(InvalidTroopException.class)
        .isThrownBy(() -> troopService.createTroop("archerr", kingdom))
        .withMessage("Created such troop can not be. Yrsssss.");
  }
}