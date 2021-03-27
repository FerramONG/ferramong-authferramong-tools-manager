package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.models.ToolsListFilter;
import ferramong.toolsmanager.services.ToolsManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ToolsManagerController {

    private final ToolsManagerService toolsManagerService;

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
        return toolsManagerService.listTools(
                ToolsListFilter.builder()
                        .toolName(toolName)
                        .build()
        );
    }
}
