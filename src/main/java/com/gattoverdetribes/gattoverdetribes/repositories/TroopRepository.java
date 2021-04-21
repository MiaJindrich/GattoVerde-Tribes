package com.gattoverdetribes.gattoverdetribes.repositories;

import com.gattoverdetribes.gattoverdetribes.models.troops.Troop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TroopRepository extends JpaRepository<Troop, Long> {
}