package ferramong.toolsmanager.repositories;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DataJpaTest
public class ToolsRepositoryTest {
    @Autowired private ToolsRepository repository;

    private static final int JOHN_DOE_ID = 1;
    private static final int EXISTING_TOOL_ID = 1;
    private static final String EXISTING_TOOL_NAME_START = "ham";

    @Nested class FindAllByOwnerId {
        @Test void whenDwellerOwnsTools() {
            assertThat(repository.findAllByOwnerId(JOHN_DOE_ID)).isNotEmpty();
        }

        @Test void whenDwellerDoesNotOwnTools() {
            final int dwellerId = -1;
            assertThat(repository.findAllByOwnerId(dwellerId)).isEmpty();
        }
    }

    @Nested class FindByIdAndOwnerId {
        @Test void whenToolIsFound() {
            assertThat(repository.findByIdAndOwnerId(EXISTING_TOOL_ID, JOHN_DOE_ID)).isPresent();
        }

        @Test void whenToolIsNotFound() {
            final int toolId = -1;
            assertThat(repository.findByIdAndOwnerId(toolId, JOHN_DOE_ID)).isEmpty();
        }
    }

    @Nested class DeleteByOwnerId {
        @Test void whenDwellerOwnsTools() {
            assertThat(repository.deleteAllByOwnerId(JOHN_DOE_ID)).isNotEmpty();
        }

        @Test void whenDwellerDoesNotOwnTools() {
            final int dwellerId = -1;
            assertThat(repository.deleteAllByOwnerId(dwellerId)).isEmpty();
        }
    }

    @Nested class DeleteByIdAndOwnerId {
        @Test void whenToolIsDeleted() {
            assertThat(repository.deleteByIdAndOwnerId(EXISTING_TOOL_ID, JOHN_DOE_ID)).isPresent();
        }

        @Test void whenToolIsNotFound() {
            final int toolId = -1;
            assertThat(repository.findByIdAndOwnerId(toolId, JOHN_DOE_ID)).isEmpty();
        }
    }

    @Nested class FindByNameStartingWith {
        @Test void whenToolNameIsNull() {
            assertThatNoException().isThrownBy(() -> repository.findByNameStartingWith(null));
            assertThat(repository.findByNameStartingWith(null)).isEmpty();
        }

        @Test void whenToolNameIsBlank() {
            assertThat(repository.findByNameStartingWith("")).isNotEmpty();
        }

        @Test void whenToolNameMatchesExistingTools() {
            assertThat(repository.findByNameStartingWith(EXISTING_TOOL_NAME_START)).isNotEmpty();
        }
    }
}
