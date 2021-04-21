package com.gattoverdetribes.gattoverdetribes.servicesTests;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertTrue;

import com.gattoverdetribes.gattoverdetribes.dtos.LoginRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterRequestDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.IncorrectPasswordException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingParameterException;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.repositories.PlayerRepository;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import com.gattoverdetribes.gattoverdetribes.services.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerServiceTest {

  @Autowired
  private PlayerService playerService;
  @Autowired
  private PlayerServiceImpl playerServiceImpl;
  @Autowired
  private PlayerRepository playerRepository;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Test
  public void registerPlayerSuccessUnitTest() {
    RegisterRequestDTO registerRequestDTO =
        new RegisterRequestDTO("rix", "rix12345", "rix's kingdomname");

    Assertions
        .assertDoesNotThrow(() -> playerService.validateRegistrationInputs(registerRequestDTO));
  }

  @Test
  public void registerPlayerWithoutNameUnitTest() throws MissingParameterException {
    RegisterRequestDTO registerRequestDTO =
        new RegisterRequestDTO("", "rix12345", "rix's kingdomname");

    assertThatExceptionOfType(MissingParameterException.class)
        .isThrownBy(() -> playerService.validateRegistrationInputs(registerRequestDTO))
        .withMessage("Fill in username you must.");
  }

  @Test
  public void loginPlayerSuccessUnitTest() {
    Player player = new Player("rix", "rix12345");
    player.setPassword(passwordEncoder.encode("rix12345"));
    playerRepository.save(player);
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO("rix", "rix12345");

    Assertions.assertDoesNotThrow(() -> playerServiceImpl.loginPlayer(loginRequestDTO));
    Assert.assertNotNull(playerServiceImpl.loginPlayer(loginRequestDTO));
  }

  @Test
  public void loginWithWrongPasswordUnitTest() throws IncorrectPasswordException {
    Player player = new Player("rix", "rix12345");
    player.setPassword(passwordEncoder.encode("rix12345"));
    playerRepository.save(player);
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO("rix", "rix1234");

    assertThatExceptionOfType(IncorrectPasswordException.class)
        .isThrownBy(() -> playerService.loginPlayer(loginRequestDTO))
        .withMessage("Your username or password correct is not.");
  }

  @Test
  public void loginWithMissingPasswordAndUsernameUnitTest() throws MissingParameterException {
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO("", "");

    assertThatExceptionOfType(MissingParameterException.class)
        .isThrownBy(() -> playerService.validateLoginInputs(loginRequestDTO))
        .withMessage("Fill in username and secret password you must.");
  }

  @Test
  public void generateTokenWithJwtTokenUnitTest() {
    Player player = new Player("rix", "rix12345");
    playerRepository.save(player);
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO("rix", "rix12345");

    Assertions.assertDoesNotThrow(() -> playerServiceImpl.generateToken(loginRequestDTO));
    Assert.assertNotNull(playerServiceImpl.generateToken(loginRequestDTO));
  }

  @Test
  public void createPlayerMatchesEncodedPasswordTest() {
    RegisterRequestDTO registerRequestDTO =
        new RegisterRequestDTO("rix", "rix12345", "rix's kingdom");
    Kingdom kingdom = new Kingdom(registerRequestDTO.getKingdomName());
    Player actualPlayer =
        playerService.createPlayer(
            registerRequestDTO.getUsername(), registerRequestDTO.getPassword(), kingdom);
    boolean matches =
        passwordEncoder.matches(registerRequestDTO.getPassword(), actualPlayer.getPassword());
    assertTrue(matches);
  }

  @Test
  public void loginWithNonEncodedPasswordTest() throws IncorrectPasswordException {
    Player player = new Player("rix", "rix12345");
    playerRepository.save(player);
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO("rix", "rix12345");

    assertThatExceptionOfType(IncorrectPasswordException.class)
        .isThrownBy(() -> playerService.loginPlayer(loginRequestDTO))
        .withMessage("Your username or password correct is not.");
  }
}