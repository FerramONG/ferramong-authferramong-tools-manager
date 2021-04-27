package ferramong.toolsmanager.services;

import ferramong.toolsmanager.config.services.ToolsManagerServiceTestConfig;
import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.exceptions.*;
import ferramong.toolsmanager.helpers.ToolEntityHelper;
import ferramong.toolsmanager.repositories.ToolsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(ToolsManagerServiceTestConfig.class)
public class ToolsServiceTest {
    @Autowired private ToolsRepository repository;
    @Autowired private ToolsService toolsService;
    @Autowired private RentalsService rentalsService;

    @Nested class GetTool {
        private Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne(1);

            when(repository.findByIdAndOwnerId(anyInt(), anyInt())).thenReturn(Optional.empty());
            when(repository.findByIdAndOwnerId(toolEntity.getId(), toolEntity.getOwnerId()))
                    .thenReturn(Optional.of(toolEntity));
        }

        @Test void whenToolIsNotFound() {
            final int toolId = 0;
            final int dwellerId = 0;

            assertThatThrownBy(() -> toolsService.getTool(toolId, dwellerId)).isInstanceOf(ToolNotFoundException.class);
        }

        @Test void whenToolIsFound() throws ToolNotFoundException {
            assertThat(toolsService.getTool(toolEntity.getId(), toolEntity.getOwnerId()))
                    .usingRecursiveComparison()
                    .isEqualTo(toolEntity);
        }
    }

    @Nested class GetAllTools {
        private static final int EXISTING_OWNER_ID = 1;
        private List<Tool> toolEntities;

        @BeforeEach void setUp() {
            toolEntities = ToolEntityHelper.buildList();

            when(repository.findAllByOwnerId(anyInt())).thenReturn(List.of());
            when(repository.findAllByOwnerId(EXISTING_OWNER_ID)).thenReturn(toolEntities);
        }

        @Test void whenDwellerOwnsTools() {
            assertThat(toolsService.getAllTools(EXISTING_OWNER_ID))
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyInAnyOrderElementsOf(toolEntities);
        }

        @Test void whenDwellerDoesNotOwnTools() {
            final var dwellerId = 0;
            assertThat(toolsService.getAllTools(dwellerId)).isEmpty();
        }
    }

    @Nested class CreateTool {
        private Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne();

            when(repository.save(toolEntity)).thenReturn(toolEntity);
        }

        @Test void whenToolIsNull() {
            assertThatThrownBy(() -> toolsService.createTool(null)).isInstanceOf(NullPointerException.class);
        }

        @Test void whenToolIsCreated() throws DwellerNotFoundException {
            assertThat(toolsService.createTool(toolEntity))
                    .usingRecursiveComparison()
                    .isEqualTo(toolEntity);
        }
    }

    @Nested class UpdateTool {
        private Tool toolEntity;

        @BeforeEach void setUp() throws RentalNotFoundException {
            toolEntity = ToolEntityHelper.buildOne(1);

            when(repository.findByIdAndOwnerId(anyInt(), anyInt())).thenReturn(Optional.empty());
            when(repository.findByIdAndOwnerId(toolEntity.getId(), toolEntity.getOwnerId()))
                    .thenReturn(Optional.of(toolEntity));
            when(repository.save(toolEntity)).thenReturn(toolEntity);
            doThrow(new RentalNotFoundException(0)).when(rentalsService).getRentedTool(anyInt(), anyInt());
        }

        @Test void whenToolIsNull() {
            assertThatThrownBy(() -> toolsService.updateTool(null)).isInstanceOf(NullPointerException.class);
        }

        @Test void whenToolIsNotFound() {
            final var missingToolEntity = ToolEntityHelper.buildOne(2);
            assertThatThrownBy(() -> toolsService.updateTool(missingToolEntity)).isInstanceOf(ToolNotFoundException.class);
        }

        @Test void whenToolIsUpdated() throws ToolNotFoundException, CannotUpdateRentedToolException {
            assertThat(toolsService.updateTool(toolEntity))
                    .usingRecursiveComparison()
                    .isEqualTo(toolEntity);
        }
    }

    @Nested class DeleteTool {
        private Tool toolEntity;

        @BeforeEach void setUp() throws RentalNotFoundException {
            toolEntity = ToolEntityHelper.buildOne(1);

            doNothing().when(repository).delete(any(Tool.class));
            when(repository.findByIdAndOwnerId(anyInt(), anyInt())).thenReturn(Optional.empty());
            when(repository.findByIdAndOwnerId(toolEntity.getId(), toolEntity.getOwnerId()))
                    .thenReturn(Optional.of(toolEntity));
            doThrow(new RentalNotFoundException(0)).when(rentalsService).getRentedTool(anyInt(), anyInt());
        }

        @Test void whenToolIsNotFound() {
            final var toolId = 0;
            final var dwellerId = 0;

            assertThatThrownBy(() -> toolsService.deleteTool(toolId, dwellerId)).isInstanceOf(ToolNotFoundException.class);
        }

        @Test void whenToolIsDeleted() throws ToolNotFoundException, CannotDeleteRentedToolException {
            assertThat(toolsService.deleteTool(toolEntity.getId(), toolEntity.getOwnerId()))
                    .usingRecursiveComparison()
                    .isEqualTo(toolEntity);
        }
    }

    @Nested class DeleteAllTools {
        private static final int EXISTING_DWELLER_ID = 1;
        private List<Tool> toolEntities;

        @BeforeEach void setUp() {
            toolEntities = ToolEntityHelper.buildList();

            when(repository.deleteAllByOwnerIdAndIdNotIn(anyInt(), anyList())).thenReturn(List.of());
            when(repository.deleteAllByOwnerIdAndIdNotIn(eq(EXISTING_DWELLER_ID), anyList())).thenReturn(toolEntities);
        }

        @Test void whenDwellerOwnsTools() {
            assertThat(toolsService.deleteAllTools(EXISTING_DWELLER_ID))
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyInAnyOrderElementsOf(toolEntities);
        }

        @Test void whenDwellerDoesNotOwnTools() {
            final var dwellerId = 0;
            assertThat(toolsService.deleteAllTools(dwellerId)).isEmpty();
        }
    }
}
