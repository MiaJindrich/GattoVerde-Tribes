package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.dtos.LoginRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterResponseDTO;
import com.gattoverdetribes.gattoverdetribes.exceptions.IdNotFoundException;
import com.gattoverdetribes.gattoverdetribes.exceptions.IncorrectPasswordException;
import com.gattoverdetribes.gattoverdetribes.exceptions.IncorrectUsernameException;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidPasswordException;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidUsernameException;
import com.gattoverdetribes.gattoverdetribes.exceptions.MissingParameterException;
import com.gattoverdetribes.gattoverdetribes.mappers.Mapper;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.repositories.PlayerRepository;
import com.gattoverdetribes.gattoverdetribes.security.service.PlayerDetailsService;
import com.gattoverdetribes.gattoverdetribes.security.utilities.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

  private final PlayerDetailsService playerDetailsService;
  private final JwtUtil jwtTokenUtil;
  private final PlayerRepository playerRepository;
  private final KingdomService kingdomService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final Mapper mapper;

  @Autowired
  public PlayerServiceImpl(
      PlayerDetailsService playerDetailsService,
      JwtUtil jwtTokenUtil,
      PlayerRepository playerRepository,
      KingdomService kingdomService,
      BCryptPasswordEncoder passwordEncoder,
      Mapper mapper) {
    this.playerDetailsService = playerDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.playerRepository = playerRepository;
    this.kingdomService = kingdomService;
    this.passwordEncoder = passwordEncoder;
    this.mapper = mapper;
  }

  @Override
  public Player createPlayer(String name, String password, Kingdom kingdom) {
    String encodedPassword = passwordEncoder.encode(password);
    Player player = new Player(name, encodedPassword, kingdom);
    kingdom.setPlayer(player);
    playerRepository.save(player);
    return player;
  }

  @Override
  public RegisterResponseDTO registerPlayer(RegisterRequestDTO registerRequestDTO) {
    Kingdom kingdom = kingdomService.createKingdom(registerRequestDTO.getKingdomName());
    Player player =
        createPlayer(registerRequestDTO.getUsername(), registerRequestDTO.getPassword(), kingdom);
    return mapper.playerToRegisterResponseDTO(player);
  }

  @Override
  public String loginPlayer(LoginRequestDTO loginRequestDTO) {
    validateLoginInputs(loginRequestDTO);
    Player player = checkOptionalPlayer(loginRequestDTO.getUsername());

    if (!passwordEncoder.matches(loginRequestDTO.getPassword(), player.getPassword())) {
      throw new IncorrectPasswordException("Your username or password correct is not.");
    } else {
      return generateToken(loginRequestDTO);
    }
  }

  @Override
  public Player checkOptionalPlayer(String username) throws IncorrectUsernameException {
    if (playerRepository.findByUsername(username).isPresent()) {
      return playerRepository.findByUsername(username).get();
    }
    throw new IncorrectUsernameException("No player of such name I see. Hrrmmm.");
  }

  @Override
  public void validateRegistrationInputs(RegisterRequestDTO registerRequestDTO) {

    if (isNullOrEmpty(registerRequestDTO.getUsername())) {
      throw new MissingParameterException("Fill in username you must.");
    } else if (isNullOrEmpty(registerRequestDTO.getPassword())) {
      throw new MissingParameterException("Fill in secret password you must.");
    } else if (isNullOrEmpty(registerRequestDTO.getKingdomName())) {
      throw new MissingParameterException("Fill in your kingdom's name you must. Yes, hrrmmm.");
    } else if (registerRequestDTO.getPassword().length() < 8) {
      throw new InvalidPasswordException("Be 8 characters your secret password must. Hrmm.");
    } else if (playerRepository.existsByUsername(registerRequestDTO.getUsername())) {
      throw new InvalidUsernameException(
          "Existing in this world some other entity of the same name already is.");
    }
  }

  @Override
  public void validateLoginInputs(LoginRequestDTO loginRequestDTO) {

    if (isNullOrEmpty(loginRequestDTO.getUsername())
        && isNullOrEmpty(loginRequestDTO.getPassword())) {
      throw new MissingParameterException("Fill in username and secret password you must.");
    } else if (isNullOrEmpty(loginRequestDTO.getUsername())) {
      throw new MissingParameterException("Fill in username you must. Yes, hrrmmm.");
    } else if (isNullOrEmpty(loginRequestDTO.getPassword())) {
      throw new MissingParameterException("Fill in secret password you must, hrrmmm.");
    }
  }

  public String generateToken(LoginRequestDTO loginRequestDTO) {
    final UserDetails userDetails =
        playerDetailsService.loadUserByUsername(loginRequestDTO.getUsername());
    return jwtTokenUtil.generateToken(userDetails);
  }

  private boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }

  @Override
  public Player extractPlayerFromToken(String token) {
    String subToken = token.substring(7);
    String playerName = jwtTokenUtil.extractUsername(subToken);
    return findPlayerByUsername(playerName);
  }

  @Override
  public Player findPlayerByUsername(String username) {
    return playerRepository.findByUsername(username).orElse(null);
  }

  @Override
  public Long findPlayersIdByUsername(String username) {
    Player player = checkOptionalPlayer(username);
    if (player == null) {
      return null;
    }
    return player.getId();
  }

  @Override
  public Player findPlayerById(Long id) {
    return playerRepository.findById(id).orElseThrow((IdNotFoundException::new));
  }

  @Override
  public void savePlayer(Player player) {
    playerRepository.save(player);
  }

  @Override
  public List<Player> findAllPlayers() {
    return playerRepository.findAll();
  }

  @Override
  public Boolean existsPlayerByUsername(String username) {
    return playerRepository.existsByUsername(username);
  }

  @Override
  public void deletePlayer(Player player) {
    if (playerRepository.findByUsername(player.getUsername()).isPresent()) {
      playerRepository.delete(player);
    }
  }

  @Override
  public Player findPlayerByKingdomId(Long id) {
    return playerRepository.findPlayerByKingdomId(id);
  }
}

