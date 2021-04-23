package ferramong.toolsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SearchByDwellerResult {
    private List<RentedTool> ownedTools;
    private List<RentedTool> rentedTools;

    public SearchByDwellerResult() {
        ownedTools = new ArrayList<>();
        rentedTools = new ArrayList<>();
    }
}
