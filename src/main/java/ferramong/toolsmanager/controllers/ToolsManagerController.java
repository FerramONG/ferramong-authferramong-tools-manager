package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.converters.ToolDtoConverter;
import ferramong.toolsmanager.dto.DeletedToolResponse;
import ferramong.toolsmanager.dto.ErrorResponse;
import ferramong.toolsmanager.dto.Tool;
import ferramong.toolsmanager.dto.ToolsManagerRequest;
import ferramong.toolsmanager.exceptions.ToolNotFoundException;
import ferramong.toolsmanager.services.ToolsManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/tools")
@CrossOrigin(
        origins = CorsConfiguration.ALL,
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@AllArgsConstructor
@Tag(name = "Tools Manager API", description = "Manage dwellers' tools")
public class ToolsManagerController {
    private final ToolsManagerService toolsManagerService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "List tools by owner.",
            parameters = @Parameter(name = "dwellerId", description = "Tools owner ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tools owned by dweller."),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public List<Tool> getDwellerTools(@RequestHeader("dwellerId") int dwellerId) {
        var entities = toolsManagerService.getAllTools(dwellerId);
        return entities.stream()
                .map(ToolDtoConverter::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{toolId}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve an existing tool.",
            parameters = {
                    @Parameter(name = "dwellerId", description = "Tool owner ID", required = true),
                    @Parameter(name = "toolId", description = "Tool ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved existing tool."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tool not found.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Tool getDwellerTool(@RequestHeader("dwellerId") int dwellerId, @PathVariable("toolId") int toolId)
            throws ToolNotFoundException {
        var entity = toolsManagerService.getTool(toolId, dwellerId);
        return ToolDtoConverter.from(entity);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Register a new tool.",
            parameters = @Parameter(name = "dwellerId", description = "Tool owner ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully registered new tool."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dweller not found.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Tool createTool(@RequestBody @Valid ToolsManagerRequest tool, @RequestHeader("dwellerId") int dwellerId) {
        var toolToCreate = ferramong.toolsmanager.entities.Tool.builder()
                .name(tool.getName())
                .category(tool.getCategory())
                .description(tool.getDescription())
                .instructions(tool.getInstructions())
                .price(tool.getPrice())
                .availableFrom(tool.getAvailableFrom())
                .availableUntil(tool.getAvailableUntil())
                .ownerId(dwellerId)
                .build();

        var createdTool = toolsManagerService.createTool(toolToCreate);
        return ToolDtoConverter.from(createdTool);
    }

    @PutMapping(path = "/{toolId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing tool.",
            parameters = {
                    @Parameter(name = "dwellerId", description = "Tool owner ID", required = true),
                    @Parameter(name = "toolId", description = "Tool ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated existing tool."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tool not found.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Tool updateTool(
            @RequestBody @Valid ToolsManagerRequest tool,
            @RequestHeader("dwellerId") int dwellerId,
            @PathVariable("toolId") int toolId
    ) throws ToolNotFoundException {
        var toolToUpdate = ferramong.toolsmanager.entities.Tool.builder()
                .id(toolId)
                .name(tool.getName())
                .category(tool.getCategory())
                .description(tool.getDescription())
                .instructions(tool.getInstructions())
                .price(tool.getPrice())
                .availableFrom(tool.getAvailableFrom())
                .availableUntil(tool.getAvailableUntil())
                .ownerId(dwellerId)
                .build();

        var updatedTool = toolsManagerService.updateTool(toolToUpdate);
        return ToolDtoConverter.from(updatedTool);
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Remove all tools from a single owner.",
            parameters = @Parameter(name = "dwellerId", description = "Tool owner ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully removed all tools from dweller."),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public List<DeletedToolResponse> deleteAllTools(@RequestHeader("dwellerId") int dwellerId) {
        var deletedTools = toolsManagerService.deleteAllTools(dwellerId);

        return deletedTools.stream()
                .map(it -> new DeletedToolResponse(it.getId()))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/{toolId}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Remove an existing tool.",
            parameters = {
                    @Parameter(name = "dwellerId", description = "Tool owner ID", required = true),
                    @Parameter(name = "toolId", description = "Tool ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully removed existing tool."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tool not found.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Tool deleteTool(@RequestHeader("dwellerId") int dwellerId, @PathVariable("toolId") int toolId)
            throws ToolNotFoundException {
        var deletedTool = toolsManagerService.deleteTool(toolId, dwellerId);
        return ToolDtoConverter.from(deletedTool);
    }
}
