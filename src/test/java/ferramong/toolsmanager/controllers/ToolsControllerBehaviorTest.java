package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.config.controllers.ToolsManagerControllerTestConfig;
import ferramong.toolsmanager.converters.ToolDtoConverter;
import ferramong.toolsmanager.dto.Tool;
import ferramong.toolsmanager.dto.ToolRequest;
import ferramong.toolsmanager.exceptions.ToolNotFoundException;
import ferramong.toolsmanager.helpers.ToolEntityHelper;
import ferramong.toolsmanager.services.ToolsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static ferramong.toolsmanager.helpers.JsonHelper.toJson;
import static org.hamcrest.Matchers.anything;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToolsController.class)
@Import(ToolsManagerControllerTestConfig.class)
public class ToolsControllerBehaviorTest {
    @Autowired private ToolsController controller;
    @Autowired private ToolsService service;
    @Autowired private MockMvc mockMvc;

    @Nested class GetDwellerTools {
        private List<Tool> responseBody;

        @BeforeEach void setUp() {
            final var toolEntities = ToolEntityHelper.buildList();
            final int ownerId = toolEntities.get(0).getOwnerId();

            responseBody = toolEntities.stream()
                    .map(ToolDtoConverter::from)
                    .collect(Collectors.toList());

            when(service.getAllTools(anyInt())).thenReturn(List.of());
            when(service.getAllTools(ownerId)).thenReturn(toolEntities);
        }

        @Test void whenDwellerOwnsTools() throws Exception {
            final int dwellerId = responseBody.get(0).getOwnerId();

            mockMvc.perform(get("/tools").header("dwellerId", dwellerId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().json(toJson(responseBody)));
        }

        @Test void whenDwellerDoesNotOwnTools() throws Exception {
            mockMvc.perform(get("/tools").header("dwellerId", -1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().json(toJson(List.of())));
        }
    }

    @Nested class GetDwellerTool {
        private Tool responseBody;

        @BeforeEach void setUp() throws ToolNotFoundException {
            final var toolEntity = ToolEntityHelper.buildOne();

            responseBody = ToolDtoConverter.from(toolEntity);

            doThrow(new ToolNotFoundException(0)).when(service).getTool(anyInt(), anyInt());
            doReturn(toolEntity).when(service).getTool(toolEntity.getId(), toolEntity.getOwnerId());
        }

        @Test void whenToolIsFound() throws Exception {
            final var toolId = responseBody.getId();
            final var dwellerId = responseBody.getOwnerId();

            mockMvc.perform(get("/tools/" + toolId).header("dwellerId", dwellerId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().json(toJson(responseBody)));
        }

        @Test void whenToolIsNotFound() throws Exception {
            mockMvc.perform(get("/tools/-1").header("dwellerId", -1))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("message", anything()));
        }
    }

    @Nested class CreateTool {
        private ToolRequest requestBody;
        private Tool responseBody;
        private ferramong.toolsmanager.entities.Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne();

            responseBody = ToolDtoConverter.from(toolEntity);
            requestBody = ToolRequest.builder()
                    .name(toolEntity.getName())
                    .category(toolEntity.getCategory())
                    .description(toolEntity.getDescription())
                    .instructions(toolEntity.getInstructions())
                    .price(toolEntity.getPrice())
                    .availableFrom(toolEntity.getAvailableFrom())
                    .availableUntil(toolEntity.getAvailableUntil())
                    .build();
        }

        @Test void whenDwellerExistsAndToolIsCreated() throws Exception {
            when(service.createTool(any())).thenReturn(toolEntity);

            mockMvc.perform(
                    post("/tools")
                            .header("dwellerId", responseBody.getOwnerId())
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(toJson(requestBody)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(toJson(responseBody)));
        }

        @Test void whenDwellerDoesNotExist() throws Exception {
            // TODO: Implement validation for dweller ID.
        }
    }

    @Nested class UpdateTool {
        private ToolRequest requestBody;
        private Tool responseBody;
        private ferramong.toolsmanager.entities.Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne();

            responseBody = ToolDtoConverter.from(toolEntity);
            requestBody = ToolRequest.builder()
                    .name(toolEntity.getName())
                    .category(toolEntity.getCategory())
                    .description(toolEntity.getDescription())
                    .instructions(toolEntity.getInstructions())
                    .price(toolEntity.getPrice())
                    .availableFrom(toolEntity.getAvailableFrom())
                    .availableUntil(toolEntity.getAvailableUntil())
                    .build();
        }

        @Test void whenToolExistsAndToolIsUpdated() throws Exception {
            when(service.updateTool(any())).thenReturn(toolEntity);

            mockMvc.perform(
                    put("/tools/1")
                            .header("dwellerId",1)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(toJson(requestBody)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJson(responseBody)));
        }

        @Test void whenToolDoesNotExist() throws Exception {
            when(service.updateTool(any())).thenThrow(ToolNotFoundException.class);

            mockMvc.perform(
                    put("/tools/1")
                            .header("dwellerId",1)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(toJson(requestBody)))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("message", anything()));
        }
    }

    @Nested class DeleteAllTools {
        private List<Tool> responseBody;
        private List<ferramong.toolsmanager.entities.Tool> toolEntities;

        @BeforeEach void setUp() {
            toolEntities = ToolEntityHelper.buildList();
            responseBody = toolEntities.stream()
                    .map(it -> Tool.builder()
                            .id(it.getId())
                            .name(it.getName())
                            .build())
                    .collect(Collectors.toList());

            final var ownerId = toolEntities.get(0).getOwnerId();

            doReturn(List.of()).when(service).deleteAllTools(anyInt());
            doReturn(toolEntities).when(service).deleteAllTools(ownerId);
        }

        @Test void whenDwellerOwnsToolsAndToolsAreDeleted() throws Exception {
            final var dwellerId = toolEntities.get(0).getOwnerId();

            mockMvc.perform(delete("/tools").header("dwellerId", dwellerId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().json(toJson(responseBody)));
        }

        @Test void whenDwellerDoesNotOwnTools() throws Exception {
            mockMvc.perform(delete("/tools").header("dwellerId", -1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().json(toJson(List.of())));
        }
    }

    @Nested class DeleteTool {
        private Tool responseBody;

        @BeforeEach void setUp() throws ToolNotFoundException {
            var toolEntity = ToolEntityHelper.buildOne();
            responseBody = ToolDtoConverter.from(toolEntity);

            doThrow(new ToolNotFoundException(0)).when(service).deleteTool(anyInt(), anyInt());
            doReturn(toolEntity).when(service).deleteTool(toolEntity.getOwnerId(), toolEntity.getId());
        }

        @Test void whenToolExistsAndToolIsDeleted() throws Exception {
            final int toolId = responseBody.getId();
            final int dwellerId = responseBody.getOwnerId();

            mockMvc.perform(delete("/tools/" + toolId).header("dwellerId", dwellerId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(content().json(toJson(responseBody)));
        }

        @Test void whenToolDoesNotExist() throws Exception {
            mockMvc.perform(delete("/tools/-1").header("dwellerId", -1))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("message", anything()));
        }
    }
}
