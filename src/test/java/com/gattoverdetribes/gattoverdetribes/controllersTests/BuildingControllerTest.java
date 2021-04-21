package com.gattoverdetribes.gattoverdetribes.controllersTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.gattoverdetribes.gattoverdetribes.models.Kingdom;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Mine;
import com.gattoverdetribes.gattoverdetribes.models.buildings.TownHall;
import com.gattoverdetribes.gattoverdetribes.models.resources.Gold;
import com.gattoverdetribes.gattoverdetribes.models.resources.Resource;
import com.gattoverdetribes.gattoverdetribes.repositories.BuildingRepository;
import com.gattoverdetribes.gattoverdetribes.repositories.KingdomRepository;
import com.gattoverdetribes.gattoverdetribes.models.buildings.Building;
import com.gattoverdetribes.gattoverdetribes.repositories.ResourceRepository;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BuildingControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private KingdomRepository kingdomRepository;
  @Autowired
  private ResourceRepository resourceRepository;
  @Autowired
  private BuildingRepository buildingRepository;

  String jwt;
  Building townHall;
  Building mine;
  List<Resource> resources;
  Gold gold;
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

    townHall = new TownHall();
    mine = new Mine();
    resources = new ArrayList<>();
    gold = new Gold();
    kingdom = kingdomRepository.findByName("rix's kingdom");
  }

  @Test
  public void getBuildings_WithBuildings() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/kingdom/buildings").accept(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().isOk())
        .andExpect(content().json("[\n"
            + "    {\n"
            + "        \"id\": 1,\n"
            + "        \"type\": \"ACADEMY\",\n"
            + "        \"level\": 1,\n"
            + "        \"hp\": 800\n"
            + "    },\n"
            + "    {\n"
            + "        \"id\": 2,\n"
            + "        \"type\": \"TOWNHALL\",\n"
            + "        \"level\": 1,\n"
            + "        \"hp\": 1000\n"
            + "    },\n"
            + "    {\n"
            + "        \"id\": 3,\n"
            + "        \"type\": \"FARM\",\n"
            + "        \"level\": 1,\n"
            + "        \"hp\": 500\n"
            + "    },\n"
            + "    {\n"
            + "        \"id\": 4,\n"
            + "        \"type\": \"MINE\",\n"
            + "        \"level\": 1,\n"
            + "        \"hp\": 500\n"
            + "    }\n"
            + "]"));
  }

  @Test
  public void getBuildings_WithoutBuildings() throws Exception {
    buildingRepository.deleteAll(buildingRepository.findAll());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/kingdom/buildings").accept(MediaType.APPLICATION_JSON)
                .header("X-tribes-token", "Bearer " + jwt))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void createBuilding_200status() throws Exception {
    final String json = "{\n"
        + "    \"type\": \"farm\"\n"
        + "}";

    List<Resource> resources = new ArrayList<>();
    Gold gold = new Gold();

    gold.setAmount(500);

    resources.add(gold);
    Kingdom kingdom = kingdomRepository.findByName("rix's kingdom");

    kingdom.setResources(resources);

    resourceRepository.save(gold);
    kingdomRepository.save(kingdom);

    mockMvc
        .perform(MockMvcRequestBuilders.post("/kingdom/buildings")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(200));
  }

  @Test
  public void createBuilding_EmptyBody_400status() throws Exception {
    final String json = "{}";

    mockMvc
        .perform(MockMvcRequestBuilders.post("/kingdom/buildings")
            .content(json).contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(400));
  }

  @Test
  public void createBuilding_InvalidBuilding_406status() throws Exception {
    final String json = "{\n"
        + "    \"type\": \"barracks\"\n"
        + "}";

    mockMvc
        .perform(MockMvcRequestBuilders.post("/kingdom/buildings")
            .content(json).contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(406));
  }

  @Test
  public void createBuilding_NotEnoughResources_409status() throws Exception {
    final String json = "{\n"
        + "    \"type\": \"academy\"\n"
        + "}";
    resourceRepository.deleteAll();

    mockMvc
        .perform(MockMvcRequestBuilders.post("/kingdom/buildings")
            .content(json).contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(409));
  }

  @Test
  public void upgradeBuilding_200status() throws Exception {
    gold.setAmount(2000);
    resources.add(gold);
    kingdom.setResources(resources);
    townHall.setKingdom(kingdom);
    townHall.setLevel(1);

    buildingRepository.save(townHall);
    resourceRepository.save(gold);
    kingdomRepository.save(kingdom);

    Long townHallId = townHall.getId();

    mockMvc
        .perform(MockMvcRequestBuilders.put("/kingdom/buildings/" + townHallId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$.level").value(townHall.getLevel() + 1));
  }

  @Test
  public void upgradeBuilding_404status() throws Exception {
    buildingRepository.deleteAll();

    mockMvc
        .perform(MockMvcRequestBuilders.put("/kingdom/buildings/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(404))
        .andExpect(
            content().json("{\"status\":\"error\",\"message\": \"Found this id was not.\"}"));
  }

  @Test
  public void upgradeBuilding_406status() throws Exception {
    gold.setAmount(0);
    resources.add(gold);
    kingdom.setResources(resources);
    townHall.setLevel(1);
    townHall.setKingdom(kingdom);
    mine.setLevel(1);
    mine.setKingdom(kingdom);

    buildingRepository.save(townHall);
    buildingRepository.save(mine);
    resourceRepository.save(gold);
    kingdomRepository.save(kingdom);

    Long idMine = mine.getId();

    mockMvc
        .perform(MockMvcRequestBuilders.put("/kingdom/buildings/" + idMine)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(406))
        .andExpect(
            content().json(
                "{\"status\":\"error\","
                    + "\"message\": \"Building level cannot be higher than Townhall level.\"}"));
  }

  @Test
  public void upgradeBuilding_409status() throws Exception {
    resourceRepository.deleteAll();
    gold.setAmount(0);
    resources.add(gold);
    kingdom.setResources(resources);
    townHall.setKingdom(kingdom);
    townHall.setLevel(1);

    buildingRepository.save(townHall);
    resourceRepository.save(gold);
    kingdomRepository.save(kingdom);

    Long townHallId = townHall.getId();

    mockMvc
        .perform(MockMvcRequestBuilders.put("/kingdom/buildings/" + townHallId)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().is(409))
        .andExpect(content().json(
            "{\"status\":\"error\",\"message\": \"Enough gold in your treasury there is not.\"}"));
  }
}
