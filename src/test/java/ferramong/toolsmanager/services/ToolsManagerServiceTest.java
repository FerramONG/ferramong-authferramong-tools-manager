package ferramong.toolsmanager.services;

import ferramong.toolsmanager.config.ToolsManagerServiceTestConfig;
import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.exceptions.ToolNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(ToolsManagerServiceTestConfig.class)
public class ToolsManagerServiceTest {
    @Autowired private ToolsRepository repository;
    @Autowired private ToolsManagerService service;

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

            assertThatThrownBy(() -> service.getTool(toolId, dwellerId)).isInstanceOf(ToolNotFoundException.class);
        }

        @Test void whenToolIsFound() throws ToolNotFoundException {
            assertThat(service.getTool(toolEntity.getId(), toolEntity.getOwnerId()))
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
            assertThat(service.getAllTools(EXISTING_OWNER_ID))
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyInAnyOrderElementsOf(toolEntities);
        }

        @Test void whenDwellerDoesNotOwnTools() {
            final var dwellerId = 0;
            assertThat(service.getAllTools(dwellerId)).isEmpty();
        }
    }

    @Nested class CreateTool {
        private Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne();

            when(repository.save(toolEntity)).thenReturn(toolEntity);
        }

        @Test void whenToolIsNull() {
            assertThatThrownBy(() -> service.createTool(null)).isInstanceOf(NullPointerException.class);
        }

        @Test void whenToolIsCreated() {
            assertThat(service.createTool(toolEntity))
                    .usingRecursiveComparison()
                    .isEqualTo(toolEntity);
        }
    }

    @Nested class UpdateTool {
        private Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne(1);

            when(repository.findByIdAndOwnerId(anyInt(), anyInt())).thenReturn(Optional.empty());
            when(repository.findByIdAndOwnerId(toolEntity.getId(), toolEntity.getOwnerId()))
                    .thenReturn(Optional.of(toolEntity));
            when(repository.save(toolEntity)).thenReturn(toolEntity);
        }

        @Test void whenToolIsNull() {
            assertThatThrownBy(() -> service.updateTool(null)).isInstanceOf(NullPointerException.class);
        }

        @Test void whenToolIsNotFound() {
            final var missingToolEntity = ToolEntityHelper.buildOne(2);
            assertThatThrownBy(() -> service.updateTool(missingToolEntity)).isInstanceOf(ToolNotFoundException.class);
        }

        @Test void whenToolIsUpdated() throws ToolNotFoundException {
            assertThat(service.updateTool(toolEntity))
                    .usingRecursiveComparison()
                    .isEqualTo(toolEntity);
        }
    }

    @Nested class DeleteTool {
        private Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne(1);

            when(repository.deleteByIdAndOwnerId(anyInt(), anyInt())).thenReturn(Optional.empty());
            when(repository.deleteByIdAndOwnerId(toolEntity.getId(), toolEntity.getOwnerId()))
                    .thenReturn(Optional.of(toolEntity));
        }

        @Test void whenToolIsNotFound() {
            final var toolId = 0;
            final var dwellerId = 0;

            assertThatThrownBy(() -> service.deleteTool(toolId, dwellerId)).isInstanceOf(ToolNotFoundException.class);
        }

        @Test void whenToolIsDeleted() throws ToolNotFoundException {
            assertThat(service.deleteTool(toolEntity.getId(), toolEntity.getOwnerId()))
                    .usingRecursiveComparison()
                    .isEqualTo(toolEntity);
        }
    }

    @Nested class DeleteAllTools {
        private static final int EXISTING_DWELLER_ID = 1;
        private List<Tool> toolEntities;

        @BeforeEach void setUp() {
            toolEntities = ToolEntityHelper.buildList();

            when(repository.deleteAllByOwnerId(anyInt())).thenReturn(List.of());
            when(repository.deleteAllByOwnerId(EXISTING_DWELLER_ID)).thenReturn(toolEntities);
        }

        @Test void whenDwellerOwnsTools() {
            assertThat(service.deleteAllTools(EXISTING_DWELLER_ID))
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyInAnyOrderElementsOf(toolEntities);
        }

        @Test void whenDwellerDoesNotOwnTools() {
            final var dwellerId = 0;
            assertThat(service.deleteAllTools(dwellerId)).isEmpty();
        }
    }
}
