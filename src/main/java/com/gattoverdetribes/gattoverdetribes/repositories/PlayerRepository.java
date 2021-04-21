package com.gattoverdetribes.gattoverdetribes.repositories;

import com.gattoverdetribes.gattoverdetribes.models.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  Optional<Player> findByUsername(String username);

  Boolean existsByUsername(String username);

  Player findPlayerByKingdomId(Long id);
}
