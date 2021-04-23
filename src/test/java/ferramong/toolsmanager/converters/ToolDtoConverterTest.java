package ferramong.toolsmanager.converters;

import ferramong.toolsmanager.dto.Tool;
import ferramong.toolsmanager.helpers.ToolEntityHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ToolDtoConverterTest {

    @Nested class FromToolDto {
        private ferramong.toolsmanager.entities.Tool toolEntity;

        @BeforeEach void setUp() {
            toolEntity = ToolEntityHelper.buildOne();
        }

        @Test void whenToolEntityIsNull() {
            toolEntity = null;
            assertThat(ToolDtoConverter.from(toolEntity)).isNull();
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
                    .isExactlyInstanceOf(Tool.class)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedToolDto);
        }
    }
}
