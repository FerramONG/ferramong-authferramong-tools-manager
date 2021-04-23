package ferramong.toolsmanager.services;

import ferramong.toolsmanager.clients.FerramongAuthClient;
import ferramong.toolsmanager.converters.RentedToolConverter;
import ferramong.toolsmanager.dto.RentedTool;
import ferramong.toolsmanager.dto.SearchByDwellerResult;
import ferramong.toolsmanager.dto.SearchByToolResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SearchService {
    private final RentalsService rentalsService;
    private final ToolsService toolsService;
    private final FerramongAuthClient authClient;

    public SearchByToolResult searchByTool(String toolName, boolean fetchAvailable, boolean fetchRented) {
        List<RentedTool> availableTools = List.of();
        List<RentedTool> rentedTools = List.of();

        if (fetchAvailable) {
            availableTools = toolsService.getAllAvailableTools(toolName).stream()
                    .filter(Objects::nonNull)
                    .map(RentedToolConverter::from)
                    .collect(Collectors.toList());
        }

        if (fetchRented) {
            rentedTools = rentalsService.getAllRentedTools(toolName).stream()
                    .filter(Objects::nonNull)
                    .map(RentedToolConverter::from)
                    .collect(Collectors.toList());
        }

        return SearchByToolResult.builder()
                .availableTools(availableTools)
                .rentedTools(rentedTools)
                .build();
    }

    public SearchByDwellerResult searchByDweller(String dwellerName, boolean fetchOwned, boolean fetchRented) {
        List<RentedTool> ownedTools = List.of();
        List<RentedTool> rentedTools = List.of();

        try {
            final var dweller = authClient.getDwellerByName(dwellerName).block();
            final var ownedOrRentedTools = rentalsService.getAllRentedOrOwnedTools(dweller.getId()).stream()
                    .filter(Objects::nonNull)
                    .map(RentedToolConverter::from)
                    .collect(Collectors.toList());

            if (fetchOwned) {
                ownedTools = getDwellerOwnedTools(dweller.getId(), ownedOrRentedTools);
            }

            if (fetchRented) {
                rentedTools = getDwellerRentedTools(dweller.getId(), ownedOrRentedTools);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return SearchByDwellerResult.builder()
                .ownedTools(ownedTools)
                .rentedTools(rentedTools)
                .build();
    }

    private List<RentedTool> getDwellerOwnedTools(int dwellerId, List<RentedTool> ownedOrRentedTools) {
        final var ownedTools = toolsService.getAllAvailableTools(dwellerId).stream()
                .filter(Objects::nonNull)
                .map(RentedToolConverter::from)
                .collect(Collectors.toList());

        ownedTools.addAll(
                ownedOrRentedTools.stream()
                    .filter(it -> it.getTool().getOwnerId().equals(dwellerId))
                    .collect(Collectors.toList())
        );

        return ownedTools;
    }

    private List<RentedTool> getDwellerRentedTools(int dwellerId, List<RentedTool> ownedOrRentedTools) {
        return ownedOrRentedTools.stream()
                .filter(it -> !it.getTool().getOwnerId().equals(dwellerId))
                .collect(Collectors.toList());
    }
}
