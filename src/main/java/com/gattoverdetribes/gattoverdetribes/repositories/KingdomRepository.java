package com.gattoverdetribes.gattoverdetribes.repositories;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KingdomRepository extends JpaRepository<Kingdom, Long> {

  Kingdom findByPlayer(Player player);

  @Query(value = "SELECT kingdoms.* FROM kingdoms Join players "
      + "ON kingdoms.id = players.kingdom_id where players.id = ?", nativeQuery = true)
  Kingdom findByPlayerId(Long id);

  Kingdom findByName(String name);

  boolean existsById(Long id);
}
