package com.gattoverdetribes.gattoverdetribes.servicesTests;

import com.gattoverdetribes.gattoverdetribes.ExternalConfig;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExternalConfigTest {

  @Test
  public void testInstance() {
    Assertions.assertNotNull(ExternalConfig.getInstance());
  }

  @Test
  public void testDefaultValues() {
    Assertions.assertEquals(500, ExternalConfig.getInstance().getResourceStartGold());
    Assertions.assertEquals(500, ExternalConfig.getInstance().getResourceStartFood());
  }

  @Test
  public void testValuesSize() throws IOException {
    List<Field> configFields = Arrays.stream(ExternalConfig.class.getDeclaredFields())
        .filter(f -> Modifier.isPrivate(f.getModifiers()))
        .collect(Collectors.toList());
    String filename = "config/tribesconfig.properties";

    Assertions.assertEquals(configFields.size() - 1,
        Files.readAllLines(Paths.get(filename)).size());
  }
}