package com.gattoverdetribes.gattoverdetribes.repositories;

import com.gattoverdetribes.gattoverdetribes.models.resources.GameTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTimeRepository extends JpaRepository<GameTime, Long> {

  GameTime findFirstByOrderByIdAsc();
}