package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.config.controllers.ToolsManagerControllerTestConfig;
import ferramong.toolsmanager.dto.ToolRequest;
import ferramong.toolsmanager.helpers.JsonHelper;
import ferramong.toolsmanager.helpers.ToolsManagerRequestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anything;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToolsController.class)
@Import(ToolsManagerControllerTestConfig.class)
public class ToolsControllerParametersTest {
    @Autowired private ToolsController controller;
    @Autowired private MockMvc mockMvc;

    @Nested class GetDwellerTools {
        @Test void whenDwellerIdIsString() throws Exception {
            mockMvc.perform(get("/tools").header("dwellerId", "null"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenDwellerIdIsMissing() throws Exception {
            mockMvc.perform(get("/tools"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenAllParametersAreValid() throws Exception {
            mockMvc.perform(get("/tools").header("dwellerId", 1))
                    .andExpect(status().isOk());
        }
    }

    @Nested class GetDwellerTool {
        @Test void whenDwellerIdIsString() throws Exception {
            mockMvc.perform(get("/tools/1").header("dwellerId", "null"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenDwellerIdIsMissing() throws Exception {
            mockMvc.perform(get("/tools/1"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenToolIdIsString() throws Exception {
            mockMvc.perform(get("/tools/null").header("dwellerId", 1))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenAllParametersAreValid() throws Exception {
            mockMvc.perform(get("/tools/1").header("dwellerId", 1))
                    .andExpect(status().isOk());
        }
    }

    @Nested class CreateTool {
        private ToolRequest requestBody;

        @BeforeEach void setUp() {
            requestBody = ToolsManagerRequestHelper.buildOne();
        }

        @Test void whenDwellerIdIsString() throws Exception {
            mockMvc.perform(
                    post("/tools")
                            .header("dwellerId", "null")
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenDwellerIdIsMissing() throws Exception {
            mockMvc.perform(
                    post("/tools")
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenRequestBodyIsMissing() throws Exception {
            mockMvc.perform(
                    post("/tools")
                            .header("dwellerId", 1)
                            .contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenRequestBodyIsInvalid() throws Exception {
            requestBody.setName("");
            requestBody.setCategory(null);

            mockMvc.perform(
                    post("/tools")
                            .header("dwellerId", 1)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenAllParametersAreValid() throws Exception {
            mockMvc.perform(
                    post("/tools")
                            .header("dwellerId", 1)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(status().isCreated());
        }
    }

    @Nested class UpdateTool {
        private ToolRequest requestBody;

        @BeforeEach void setUp() {
            requestBody = ToolsManagerRequestHelper.buildOne();
        }

        @Test void whenDwellerIdIsString() throws Exception {
            mockMvc.perform(
                    put("/tools/1")
                            .header("dwellerId", "null")
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenDwellerIdIsMissing() throws Exception {
            mockMvc.perform(
                    put("/tools/1")
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenToolIdIsString() throws Exception {
            mockMvc.perform(
                    put("/tools/null")
                            .header("dwellerId", 1)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenRequestBodyIsMissing() throws Exception {
            mockMvc.perform(
                    put("/tools/1")
                            .header("dwellerId", 1)
                            .contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenRequestBodyIsInvalid() throws Exception {
            requestBody.setName("");
            requestBody.setCategory(null);

            mockMvc.perform(
                    put("/tools/1")
                            .header("dwellerId", 1)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenAllParametersAreValid() throws Exception {
            mockMvc.perform(
                    put("/tools/1")
                            .header("dwellerId", 1)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(JsonHelper.toJson(requestBody)))
                    .andExpect(status().isOk());
        }
    }

    @Nested class DeleteAllTools {
        @Test void whenDwellerIdIsString() throws Exception {
            mockMvc.perform(delete("/tools").header("dwellerId", "null"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenDwellerIdIsMissing() throws Exception {
            mockMvc.perform(delete("/tools"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenAllParametersAreValid() throws Exception {
            mockMvc.perform(delete("/tools").header("dwellerId", 1))
                    .andExpect(status().isOk());
        }
    }

    @Nested class DeleteTool {
        @Test void whenDwellerIdIsString() throws Exception {
            mockMvc.perform(delete("/tools/1").header("dwellerId", "null"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenDwellerIdIsMissing() throws Exception {
            mockMvc.perform(delete("/tools/1"))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenToolIdIsString() throws Exception {
            mockMvc.perform(delete("/tools/null").header("dwellerId", 1))
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", anything()));
        }

        @Test void whenAllParametersAreValid() throws Exception {
            mockMvc.perform(delete("/tools/1").header("dwellerId", 1))
                    .andExpect(status().isOk());
        }
    }
}
