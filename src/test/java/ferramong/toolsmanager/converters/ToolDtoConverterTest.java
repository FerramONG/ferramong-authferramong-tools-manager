package ferramong.toolsmanager.converters;

import ferramong.toolsmanager.dto.Tool;
import ferramong.toolsmanager.helpers.ToolEntityHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ToolDtoConverterTest {
    private ferramong.toolsmanager.entities.Tool toolEntity;

    @Nested class ShouldReturnNull {
        @Test void whenToolEntityIsNull() {
            assertThat(ToolDtoConverter.from(null)).isNull();
        }
    }

    @Nested class ShouldMapFieldsFromSource {
        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne();
        }

        @Test void whenToolEntityHasValues() {
            var expectedToolDto = Tool.builder()
                    .id(toolEntity.getId())
                    .name(toolEntity.getName())
                    .category(toolEntity.getCategory())
                    .description(toolEntity.getDescription())
                    .instructions(toolEntity.getInstructions())
                    .price(toolEntity.getPrice())
                    .availableFrom(toolEntity.getAvailableFrom())
                    .availableUntil(toolEntity.getAvailableUntil())
                    .ownerId(toolEntity.getOwnerId())
                    .build();

            assertThat(ToolDtoConverter.from(toolEntity))
                    .isExactlyInstanceOf(ferramong.toolsmanager.dto.Tool.class)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedToolDto);
        }
    }
}
