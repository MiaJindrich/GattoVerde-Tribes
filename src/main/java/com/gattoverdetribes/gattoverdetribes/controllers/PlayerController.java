package com.gattoverdetribes.gattoverdetribes.controllers;

import com.gattoverdetribes.gattoverdetribes.dtos.LoginRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.LoginResponseDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterResponseDTO;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.services.PlayerService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class PlayerController {

  private final Logger logger;
  private final PlayerService playerService;

  @Autowired
  public PlayerController(Logger logger, PlayerService playerService) {
    this.logger = logger;
    this.playerService = playerService;
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterResponseDTO> registerPlayer(
      @RequestBody RegisterRequestDTO registerRequestDTO) {
    playerService.validateRegistrationInputs(registerRequestDTO);
    RegisterResponseDTO registerResponseDTO = playerService.registerPlayer(registerRequestDTO);

    logger.info(
        "New user reached /register endpoint with Post request method. The entered username was: "
            + registerRequestDTO.getUsername() + " and the entered kingdom name was: "
            + registerRequestDTO.getKingdomName() + ".");
    return ResponseEntity.ok().body(registerResponseDTO);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> loginPlayer(
      @RequestBody LoginRequestDTO loginRequestDTO) {
    String jwt = playerService.loginPlayer(loginRequestDTO);
    LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
    loginResponseDTO.setStatus("ok");
    loginResponseDTO.setToken(jwt);
    Player player = playerService.findPlayerByUsername(loginRequestDTO.getUsername());

    logger.info(
        "Player with id " + player.getId() + " and username " + player.getUsername()
            + " reached /login endpoint Post request method. The entered username was: "
            + loginRequestDTO.getUsername() + ".");
    return ResponseEntity.ok().body(loginResponseDTO);
  }
}
