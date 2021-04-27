package ferramong.toolsmanager.services;

import ferramong.toolsmanager.clients.FerramongAuthClient;
import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.exceptions.*;
import ferramong.toolsmanager.repositories.ToolsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class ToolsService {
    @Autowired private ToolsRepository toolsRepository;
    @Autowired private RentalsService rentalsService;
    @Autowired private FerramongAuthClient authClient;

    public Tool getTool(int toolId, int dwellerId) throws ToolNotFoundException {
        return toolsRepository.findByIdAndOwnerId(toolId, dwellerId)
                .orElseThrow(() -> new ToolNotFoundException(toolId));
    }

    public List<Tool> getAllTools(int dwellerId) {
        return toolsRepository.findAllByOwnerId(dwellerId);
    }

    public List<Tool> getAllAvailableTools(int dwellerId) {
        return toolsRepository.findAllAvailableByOwnerId(dwellerId);
    }

    public List<Tool> getAllAvailableTools(@NotNull String toolName) {
        return toolsRepository.findAllAvailableByNameStartsWith(toolName);
    }

    @Transactional
    public Tool createTool(@NotNull Tool tool) throws DwellerNotFoundException {
        authClient.getDwellerById(tool.getOwnerId());

        tool.setId(null);
        return toolsRepository.save(tool);
    }

    @Transactional
    public Tool updateTool(@NotNull Tool tool) throws ToolNotFoundException, CannotUpdateRentedToolException {
        var toolToUpdate = toolsRepository.findByIdAndOwnerId(tool.getId(), tool.getOwnerId());
        if (toolToUpdate.isPresent() && isToolAvailable(tool.getId(), tool.getOwnerId())) {
            return toolsRepository.save(tool);
        }

        if (toolToUpdate.isPresent()) {
            throw new CannotUpdateRentedToolException(tool.getId());
        } else {
            throw new ToolNotFoundException(tool.getId());
        }
    }

    @Transactional
    public Tool deleteTool(int toolId, int dwellerId) throws ToolNotFoundException, CannotDeleteRentedToolException {
        var toolToDeleteEntity = toolsRepository.findByIdAndOwnerId(toolId, dwellerId);
        if (toolToDeleteEntity.isPresent() && isToolAvailable(toolId, dwellerId)) {
            var toolToDelete = toolToDeleteEntity.get();
            toolsRepository.delete(toolToDelete);

            return toolToDelete;
        }

        if (toolToDeleteEntity.isPresent()) {
            throw new CannotDeleteRentedToolException(toolId);
        } else {
            throw new ToolNotFoundException(toolId);
        }


    }

    @Transactional
    public List<Tool> deleteAllTools(int dwellerId) {
        final var rentedTools = rentalsService.getAllRentedToolsByOwner(dwellerId)
                .stream()
                .map(it -> it.getTool().getId())
                .collect(Collectors.toList());

        if (rentedTools.isEmpty()) {
            return toolsRepository.deleteAllByOwnerId(dwellerId);
        } else {
            return toolsRepository.deleteAllByOwnerIdAndIdNotIn(dwellerId, rentedTools);
        }
    }

    private boolean isToolAvailable(int toolId, int ownerDwellerId) {
        try {
            rentalsService.getRentedTool(toolId, ownerDwellerId);
            return false;
        } catch (RentalNotFoundException e) {
            return true;
        }
    }
}
