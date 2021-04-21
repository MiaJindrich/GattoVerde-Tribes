package com.gattoverdetribes.gattoverdetribes.repositories;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.resources.Gold;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

  List<Resource> findAllByKingdomId(Long id);

  Gold findByKingdom(Kingdom kingdom);
}
