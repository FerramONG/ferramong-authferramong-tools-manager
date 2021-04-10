package ferramong.toolsmanager.converters;

import ferramong.toolsmanager.dto.Tool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ToolDtoConverter {

    public static Tool from(ferramong.toolsmanager.entities.Tool source) {
        if (nonNull(source)) {
            return Tool.builder()
                    .id(source.getId())
                    .name(source.getName())
                    .category(source.getCategory())
                    .description(source.getDescription())
                    .instructions(source.getInstructions())
                    .price(source.getPrice())
                    .availableFrom(source.getAvailableFrom())
                    .availableUntil(source.getAvailableUntil())
                    .ownerId(source.getOwnerId())
                    .build();
        }

        return null;
    }
}
