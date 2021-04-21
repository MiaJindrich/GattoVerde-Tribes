package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import com.gattoverdetribes.gattoverdetribes.GattoverdeTribesApplication;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.repositories.PlayerRepository;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureMockMvc
@SpringBootTest(classes = GattoverdeTribesApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerServiceUnitTest {

  @Autowired
  private PlayerService playerService;
  @MockBean
  private PlayerRepository playerRepository;

  Kingdom kingdom;
  Kingdom kingdom2;
  Player player;
  Player player2;

  @BeforeEach
  public void setup() {
    kingdom = new Kingdom("Narnia");
    kingdom2 = new Kingdom("Marnia");

    player = playerService.createPlayer("Me", "123", kingdom);
    player2 = playerService.createPlayer("You", "123", kingdom2);
  }

  @Test
  public void playerFoundByUsername() {
    when(playerRepository.findByUsername("Me"))
        .thenReturn(Optional.ofNullable(player));
    Player actualPlayer = playerService.findPlayerByUsername("Me");
    assertEquals(player, actualPlayer);
  }

  @Test
  public void playerNotFound() {
    when(playerRepository.findByUsername("He")).thenReturn(Optional.empty());
    Player actualPlayer = playerService.findPlayerByUsername(null);
    assertNull(actualPlayer);
  }

  @Test
  public void findPlayerByKingdom() {
    when(playerRepository.findPlayerByKingdomId(kingdom.getId())).thenReturn(player);
    Player actualPlayer = playerService.findPlayerByKingdomId(kingdom.getId());
    assertEquals(player, actualPlayer);
  }

  @Test
  void playerFoundById() {
    when(playerRepository.findById(1L)).thenReturn(Optional.ofNullable(player2));
    Player actualPlayer = playerService.findPlayerById(1L);
    assertEquals(player2, actualPlayer);
  }
}