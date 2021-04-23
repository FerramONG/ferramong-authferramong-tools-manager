package ferramong.toolsmanager.converters;

import ferramong.toolsmanager.dto.Tool;
import ferramong.toolsmanager.entities.Rental;
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
                    .ownerId(source.getOwnerId())
                    .availableFrom(source.getAvailableFrom())
                    .availableUntil(source.getAvailableUntil())
                    .build();
        }

        return null;
    }

    public static Tool from(Rental source) {
        if (nonNull(source)) {
            final var rentalTool = source.getTool();
            return Tool.builder()
                    .id(source.getId())
                    .name(rentalTool.getName())
                    .category(rentalTool.getCategory())
                    .description(rentalTool.getDescription())
                    .instructions(rentalTool.getInstructions())
                    .price(rentalTool.getPrice())
                    .ownerId(rentalTool.getOwnerId())
                    .availableFrom(rentalTool.getAvailableFrom())
                    .availableUntil(rentalTool.getAvailableUntil())
                    .renterId(source.getRenterId())
                    .rentFrom(source.getRentFrom())
                    .rentUntil(source.getRentUntil())
                    .expectedReturnDate(source.getExpectedReturnDate())
                    .build();
        }

        return null;
    }
}
