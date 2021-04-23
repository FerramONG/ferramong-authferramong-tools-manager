package ferramong.toolsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SearchByToolResult {
    private List<Tool> availableTools;
    private List<Tool> rentedTools;

    public SearchByToolResult() {
        availableTools = new ArrayList<>();
        rentedTools = new ArrayList<>();
    }
}
