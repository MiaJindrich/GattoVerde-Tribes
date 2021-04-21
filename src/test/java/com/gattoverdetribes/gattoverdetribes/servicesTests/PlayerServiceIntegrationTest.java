package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static org.junit.Assert.assertNotNull;

import com.gattoverdetribes.gattoverdetribes.GattoverdeTribesApplication;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.repositories.PlayerRepository;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = GattoverdeTribesApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerServiceIntegrationTest {

  @Autowired
  private PlayerRepository playerRepository;
  @Autowired
  private PlayerService playerService;

  Kingdom kingdom;
  Kingdom kingdom2;
  Player player;
  Player player2;

  @BeforeEach
  public void setup() {
    kingdom = new Kingdom("Narnia");
    kingdom2 = new Kingdom("Marnia");
    player = playerService.createPlayer("Me", "123123123", kingdom);
    player2 = playerService.createPlayer("You", "123123123", kingdom2);
  }

  @Test
  void createPlayer() {
    List<Player> beforeCreatingPlayerList = playerRepository.findAll();
    Player createdPlayer = playerService
        .createPlayer(player.getUsername(), player.getPassword(), new Kingdom("Narnia"));
    List<Player> afterCreatingPlayerList = playerRepository.findAll();
    Assert.assertNotEquals(beforeCreatingPlayerList.size(), afterCreatingPlayerList.size());
  }

  @Test
  public void createPlayerNotNullTest() {
    Player actualPlayer = playerService
        .createPlayer(player2.getUsername(), player2.getPassword(), new Kingdom("Marnia"));
    assertNotNull(actualPlayer);
  }

  @Test
  void deletePlayer() {
    List<Player> beforeDeletingPlayerList = playerRepository.findAll();
    playerService.deletePlayer(player);
    playerService.deletePlayer(player2);
    List<Player> afterDeletingPlayerList = playerRepository.findAll();
    Assert.assertNotEquals(beforeDeletingPlayerList.size(), afterDeletingPlayerList.size());
  }
}