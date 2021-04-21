package ferramong.toolsmanager.converters;

import ferramong.toolsmanager.dto.RentedTool;
import ferramong.toolsmanager.entities.Rental;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RentedToolConverter {

    public static RentedTool from(Rental source) {
        if (nonNull(source)) {
            return RentedTool.builder()
                    .renterDwellerId(source.getRenterId())
                    .rentFrom(source.getRentFrom())
                    .rentUntil(source.getRentUntil())
                    .expectedReturnDate(source.getExpectedReturnDate())
                    .tool(ToolDtoConverter.from(source.getTool()))
                    .build();
        }

        return null;
    }
}
