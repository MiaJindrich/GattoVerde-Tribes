package com.gattoverdetribes.gattoverdetribes.security.service;

import com.gattoverdetribes.gattoverdetribes.models.Player;
import com.gattoverdetribes.gattoverdetribes.repositories.PlayerRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerDetailsService implements UserDetailsService {

  private final PlayerRepository playerRepository;

  @Autowired
  public PlayerDetailsService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  private Player findPlayerByUsername(String username) {
    Optional<Player> optionalPlayer = playerRepository.findByUsername(username);
    return optionalPlayer.orElse(null);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Player player = findPlayerByUsername(username);
    String userName = player.getUsername();
    String password = player.getPassword();
    return new User(userName, password, new ArrayList<>());
  }
}
