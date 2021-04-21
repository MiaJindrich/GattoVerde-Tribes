package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Location;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.PlayerRepository;
import com.gattoverdetribes.gattoverdetribes.services.KingdomService;
import com.gattoverdetribes.gattoverdetribes.services.StarterPackService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
public class KingdomServiceTest {

  @Autowired
  private KingdomService kingdomService;

  @Autowired
  private KingdomRepository kingdomRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private StarterPackService starterPackService;

  @Test
  public void findKingdomNameByPlayerIdTest() {
    Kingdom expectedKingdom = new Kingdom("Narnia");
    Player player = new Player("user", "user123456", expectedKingdom);
    playerRepository.save(player);
    String actualKingdomName = kingdomService.getKingdomByPlayerId(player.getId()).getName();
    assertEquals(expectedKingdom.getName(), actualKingdomName);
  }

  @Test
  public void createKingdomTest() {
    Kingdom actualKingdom = kingdomService.createKingdom("Narnia");
    assertNotNull(actualKingdom);
  }

  @Test
  public void kingdomFoundByIdTest() {
    Kingdom expectedKingdom = new Kingdom("Narnia");
    kingdomRepository.save(expectedKingdom);
    Kingdom actualKingdom = kingdomService.getById(expectedKingdom.getId());
    assertEquals(expectedKingdom.getId(), actualKingdom.getId());
  }

  @Test
  public void kingdomNotFoundTest() {
    Kingdom actualKingdom = kingdomService.getById(1L);
    assertNull(actualKingdom);
  }

  @Test
  public void generateKingdomLocationTest() {
    Kingdom kingdom1 = new Kingdom("Isengard");
    Kingdom kingdom2 = new Kingdom("SomeLand");
    Kingdom kingdom3 = new Kingdom("Sweden");
    kingdom1.setLocation(starterPackService.generateKingdomLocation(kingdom1));
    kingdom2.setLocation(starterPackService.generateKingdomLocation(kingdom2));
    kingdom3.setLocation(starterPackService.generateKingdomLocation(kingdom3));
    kingdomRepository.save(kingdom1);
    kingdomRepository.save(kingdom2);
    kingdomRepository.save(kingdom3);
    List<Kingdom> kingdoms = kingdomRepository.findAll();
    List<Location> locations = kingdoms.stream().map(Kingdom::getLocation)
        .collect(Collectors.toList());
    Location location = starterPackService.generateKingdomLocation(kingdom1);
    assertFalse(locations.contains(location));
  }
}
