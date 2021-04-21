package com.gattoverdetribes.gattoverdetribes.controllersTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.TroopRepository;
import com.gattoverdetribes.gattoverdetribes.services.TroopService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TroopControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private KingdomRepository kingdomRepository;
  @Autowired
  private TroopRepository troopRepository;
  @Autowired
  private TroopService troopService;

  String jwt;
  Kingdom kingdom;

  @BeforeEach
  public void init() throws Exception {
    mockMvc
        .perform(
            post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"username\": \"rix\",\"password\": \"rix12345\",\"kingdomname\": "
                        + "\"rix's kingdom\"}"))
        .andExpect(status().isOk());

    MvcResult result =
        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"rix\",\"password\": \"rix12345\"}"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    JSONObject jsonObject = new JSONObject(response);
    jwt = jsonObject.get("token").toString();
    kingdom = kingdomRepository.findByName("rix's kingdom");
  }

  @Test
  public void getTroops_WithTroops() throws Exception {
    troopService.createTroop("archer", kingdom);
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/kingdom/troops")
            .accept(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getTroops_WithoutTroops() throws Exception {
    troopRepository.deleteAll(troopRepository.findAll());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/kingdom/troops")
                .accept(MediaType.APPLICATION_JSON)
                .header("X-tribes-token", "Bearer " + jwt))
        .andDo(print())
        .andExpect(status().isNoContent());
  }
}