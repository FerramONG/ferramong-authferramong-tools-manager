package ferramong.toolsmanager.helpers;

import ferramong.toolsmanager.dto.Tool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ToolDtoHelper {

    public static Tool buildOne() {
        return Tool.builder()
                .id(1)
                .name("name")
                .category("category")
                .description("description")
                .instructions("instructions")
                .price(1.0)
                .availableFrom(LocalDate.now())
                .availableUntil(LocalDate.now().plusWeeks(1))
                .ownerId(1)
                .build();
    }
}
