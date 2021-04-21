package com.gattoverdetribes.gattoverdetribes.services;

import com.gattoverdetribes.gattoverdetribes.models.resources.GameTime;
import com.gattoverdetribes.gattoverdetribes.repositories.GameTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TimeServiceImpl implements TimeService {

  private final GameTimeRepository gameTimeRepository;
  private final ResourceService resourceService;

  @Autowired
  public TimeServiceImpl(
      GameTimeRepository gameTimeRepository,
      ResourceService resourceService) {
    this.gameTimeRepository = gameTimeRepository;
    this.resourceService = resourceService;
  }

  @Scheduled(cron = "${cron.expression}")
  public void serverTick() {
    checkTime();
    resourceService.calculateKingdomsProduction();
  }

  @Override
  public void checkTime() {
    if (gameTimeRepository.findFirstByOrderByIdAsc() == null) {
      GameTime gameTime = new GameTime();
      gameTime.setServerStart(1607501228L);
      gameTimeRepository.save(gameTime);
    } else {
      GameTime gameTime = gameTimeRepository.findFirstByOrderByIdAsc();
      long ticks = gameTime.getTicks() + 1;
      long serverStart = gameTime.getServerStart();
      long timeNow = System.currentTimeMillis() / 1000;
      long expectedTicks = (timeNow - serverStart) / 60;
      long ticksDifference = expectedTicks - ticks;
      for (int i = 0; i < ticksDifference; i++) {
        ticks++;
      }
      gameTime.setTicks(ticks);
      gameTimeRepository.save(gameTime);
      System.out.println("There were already " + gameTime.getTicks() + " ticks since start");
    }
  }

  @Override
  public long getTimeNow() {
    return System.currentTimeMillis();
  }
}