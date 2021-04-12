package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.converters.ToolDtoConverter;
import ferramong.toolsmanager.dto.Tool;
import ferramong.toolsmanager.models.ToolsListFilter;
import ferramong.toolsmanager.services.ToolsSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/search")
@CrossOrigin(
        origins = CorsConfiguration.ALL,
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@AllArgsConstructor
@Tag(name = "Tools Search API")
public class ToolsSearchController {

    private final ToolsSearchService toolsSearchService;

    @Operation(summary = "Search tools by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tools containing searched name.")
    })
    @GetMapping(value = "/search/tools/{toolName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tool> searchTools(
            @Parameter(description = "The name to search for", required = true, example = "hammer")
            @PathVariable("toolName")
            @NotBlank
                    String toolName
    ) {
        var tools = toolsSearchService.listTools(
                ToolsListFilter.builder()
                        .toolName(toolName)
                        .build()
        );

        return tools.stream()
                .map(ToolDtoConverter::from)
                .collect(Collectors.toList());
    }
}
