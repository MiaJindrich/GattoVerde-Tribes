package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.dtos.LoginRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterRequestDTO;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterResponseDTO;
import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import java.util.List;

public interface PlayerService {

  Player findPlayerByUsername(String username);

  Player findPlayerById(Long id);

  Long findPlayersIdByUsername(String username);

  Player createPlayer(String name, String password, Kingdom kingdom);

  void savePlayer(Player player);

  List<Player> findAllPlayers();

  Boolean existsPlayerByUsername(String username);

  Player findPlayerByKingdomId(Long id);

  void deletePlayer(Player player);

  RegisterResponseDTO registerPlayer(RegisterRequestDTO registerRequestDTO);

  Player checkOptionalPlayer(String username);

  void validateRegistrationInputs(RegisterRequestDTO registerRequestDTO);

  void validateLoginInputs(LoginRequestDTO loginRequestDTO);

  String loginPlayer(LoginRequestDTO loginRequestDTO);

  Player extractPlayerFromToken(String token);
}
