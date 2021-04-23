package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.dto.ErrorResponse;
import ferramong.toolsmanager.dto.SearchByDwellerResult;
import ferramong.toolsmanager.dto.SearchByToolResult;
import ferramong.toolsmanager.services.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/search")
@CrossOrigin(
        origins = CorsConfiguration.ALL,
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@AllArgsConstructor
@Tag(name = "Search", description = "Search for tools")
public class SearchController {
    private final SearchService service;

    @GetMapping(path = "/by-tool", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Search by tool attributes.",
            parameters = {
                    @Parameter(name = "toolName", description = "Tool name"),
                    @Parameter(name = "fetchAvailable", description = "Fetch available tools?"),
                    @Parameter(name = "fetchRented", description = "Fetch rented tools?")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved search results."),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public SearchByToolResult searchByTool(
            @RequestParam(name = "toolName", defaultValue = "") String toolName,
            @RequestParam(name = "fetchAvailable", defaultValue = "true") boolean fetchAvailable,
            @RequestParam(name = "fetchRented", defaultValue = "true") boolean fetchRented
    ) {
        return service.searchByTool(toolName, fetchAvailable, fetchRented);
    }

    @GetMapping(path = "/by-dweller", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Search by dweller attributes.",
            parameters = {
                    @Parameter(name = "dwellerName", description = "Dweller name", required = true),
                    @Parameter(name = "fetchOwned", description = "Fetch owned tools?"),
                    @Parameter(name = "fetchRented", description = "Fetch rented tools?")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved search results."),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public SearchByDwellerResult searchByDweller(
            @RequestParam(name = "dwellerName") @NotBlank String dwellerName,
            @RequestParam(name = "fetchOwned", defaultValue = "true") boolean fetchOwned,
            @RequestParam(name = "fetchRented", defaultValue = "true") boolean fetchRented
    ) {
        return service.searchByDweller(dwellerName, fetchOwned, fetchRented);
    }
}
