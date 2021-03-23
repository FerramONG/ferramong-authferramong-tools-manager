package ferramong.toolsmanager.services;

import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.models.ToolsListFilter;
import ferramong.toolsmanager.repositories.ToolsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class ToolsManagerService {

    private final ToolsRepository toolsRepository;

    public List<Tool> listTools(ToolsListFilter filter) {
        if (filter == null) {
            return emptyList();
        }

        return toolsRepository.findToolsByNameAndAssociatedDweller(filter.getToolName(), filter.getDwellerName());
    }
}
