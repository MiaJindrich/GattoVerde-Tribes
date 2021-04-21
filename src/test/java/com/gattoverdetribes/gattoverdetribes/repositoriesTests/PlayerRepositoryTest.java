package com.gattoverdetribes.gattoverdetribes.repositoriesTests;

import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.PlayerRepository;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlayerRepositoryTest {

  @Autowired
  PlayerRepository playerRepository;
  @Autowired
  KingdomRepository kingdomRepository;

  Player player;
  Kingdom kingdom;

  @BeforeEach
  public void setUp() {
    player = new Player();
    player.setUsername("rix");
    player.setPassword("rix12345");
    kingdom = new Kingdom();
    kingdomRepository.save(kingdom);
  }

  @AfterEach
  public void tearDown() {
    playerRepository.deleteAll();
  }

  @Test
  public void playerExistByUserNameUnitTest() {
    playerRepository.save(player);
    Boolean existsByUsername = (playerRepository.existsByUsername("rix"));
    Assert.assertEquals(true, existsByUsername);
  }

  @Test
  public void playerExistByUserNameUnitFailedTest() {
    Boolean existsByUsername = (playerRepository.existsByUsername("rix"));
    Assert.assertEquals(false, existsByUsername);
  }

  @Test
  public void findPlayerByUsernameUnitTest() {
    playerRepository.save(player);
    var optionalPlayer = playerRepository.findByUsername(player.getUsername());
    if (optionalPlayer.isPresent()) {
      Player player1 = optionalPlayer.get();
      Assert.assertEquals(player, player1);
    }
  }

  @Test
  public void findPlayerByUsernameFailedUnitTest() {
    playerRepository.save(player);
    var optionalPlayer = playerRepository.findByUsername("hex");
    if (optionalPlayer.isPresent()) {
      Player player1 = optionalPlayer.get();
      Assert.assertNotEquals(player, player1);
    }
  }

  @Test
  public void findPlayerByUsernameFailedNullUnitTest() {

    var optionalPlayer = playerRepository.findByUsername("rix");
    if (optionalPlayer.isPresent()) {
      Player player1 = optionalPlayer.get();
      Assert.assertNull(player1);
    }
  }

  @Test
  public void findPlayerByIdUnitTest() {
    Player playerActual =
        playerRepository.save(new Player("TestName", "TestPassword", kingdom));
    Optional<Player> expected = playerRepository.findById(playerActual.getId());
    Assert.assertEquals(playerActual.getUsername(), expected.get().getUsername());
  }
}