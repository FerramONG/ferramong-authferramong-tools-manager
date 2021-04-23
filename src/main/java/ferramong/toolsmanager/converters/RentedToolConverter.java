package ferramong.toolsmanager.converters;

import ferramong.toolsmanager.dto.RentedTool;
import ferramong.toolsmanager.entities.Rental;
import ferramong.toolsmanager.entities.Tool;
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

    public static RentedTool from(Tool source) {
        if (nonNull(source)) {
            return RentedTool.builder()
                    .tool(ToolDtoConverter.from(source))
                    .build();
        }

        return null;
    }
}
