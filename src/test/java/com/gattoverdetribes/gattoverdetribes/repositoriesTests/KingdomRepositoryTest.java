package com.gattoverdetribes.gattoverdetribes.repositoriesTests;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class KingdomRepositoryTest {

  @Autowired
  KingdomRepository kingdomRepository;

  Kingdom kingdom1;
  Kingdom kingdom2;

  @BeforeEach
  public void setUp() {
    kingdom1 = new Kingdom();
    kingdom1.setName("TestKingdom");
    kingdom2 = new Kingdom();
    kingdom2.setName("TestKingdom2");
    kingdomRepository.save(kingdom1);
    kingdomRepository.save(kingdom2);
  }

  @AfterEach
  public void tearDown() {
    kingdomRepository.deleteAll();
  }

  @Test
  public void testSaveKingdom() throws Exception {
    Kingdom actualKingdom = kingdomRepository.save(kingdom1);
    Optional<Kingdom> fetchKingdom = kingdomRepository.findById(kingdom1.getId());
    Assert.assertEquals(actualKingdom.getId(), fetchKingdom.get().getId());
  }

  @Test
  public void testGetAllKingdoms() throws Exception {
    List<Kingdom> list = kingdomRepository.findAll();
    Assert.assertEquals("DummyKingdom", list.get(0).getName());
  }

  @Test
  public void testSaveKingdom_Failed() {
    Kingdom testKingdom = new Kingdom();
    testKingdom.setName("TestFailure");
    kingdomRepository.save(kingdom1);
    Kingdom fetchKingdom = kingdomRepository.findById(kingdom1.getId()).get();
    Assert.assertNotSame(testKingdom, fetchKingdom);
  }
}