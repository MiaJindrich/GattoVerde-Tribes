package com.gattoverdetribes.gattoverdetribes.controllersTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gattoverdetribes.gattoverdetribes.repositories.ResourceRepository;
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
public class ResourceControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ResourceRepository resourceRepository;

  String jwt;

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
  }

  @Test
  public void getResources_WithoutResources() throws Exception {
    resourceRepository.deleteAll(resourceRepository.findAll());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/kingdom/resources").accept(MediaType.APPLICATION_JSON)
                .header("X-tribes-token", "Bearer " + jwt))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void getResources_WithResources() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/kingdom/resources").accept(MediaType.APPLICATION_JSON)
            .header("X-tribes-token", "Bearer " + jwt))
        .andExpect(status().isOk())
        .andExpect(content().json("[\n"
            + "    {\n"
            + "        \"type\": \"food\",\n"
            + "        \"amount\": 500,\n"
            + "        \"generation\": 0\n"
            + "    },\n"
            + "    {\n"
            + "        \"type\": \"gold\",\n"
            + "        \"amount\": 500,\n"
            + "        \"generation\": 0\n"
            + "    }\n"
            + "]")).andDo(print());
  }
}