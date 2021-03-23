package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.models.ToolsListFilter;
import ferramong.toolsmanager.services.ToolsManagerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@AllArgsConstructor
public class ToolsManagerController {

    private final ToolsManagerService toolsManagerService;

    @GetMapping("/search/{toolName}/tools")
    public List<Tool> searchTools(@PathVariable("toolName") @NotBlank String toolName) {
        var filter = ToolsListFilter.builder()
                .toolName(toolName)
                .build();

        return toolsManagerService.listTools(filter);
    }
}
