package com.gattoverdetribes.gattoverdetribes;

import com.gattoverdetribes.gattoverdetribes.controllers.PlayerController;
import com.gattoverdetribes.gattoverdetribes.dtos.RegisterRequestDTO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class Logs4jTest {

  @Autowired
  private PlayerController playerController;


  @Transactional
  @Test
  public void newLogWasAddedToAppLogFile() throws InterruptedException {
    Long lineCountBefore = countLines();

    RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO("rix", "rix123456",
        "rix's kingdom");
    playerController.registerPlayer(registerRequestDTO);

    Thread.sleep(5000);

    Long lineCountAfter = countLines();

    Assert.assertNotEquals(lineCountBefore, lineCountAfter);
  }

  public long countLines() {
    long lineCount = 0;
    try (Stream<String> stream = Files.lines(Path.of("logs/app.log"), StandardCharsets.UTF_8)) {
      lineCount = stream.count();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lineCount;
  }
}
