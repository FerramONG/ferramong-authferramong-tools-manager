package ferramong.toolsmanager.helpers;

import ferramong.toolsmanager.dto.ToolsManagerRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ToolsManagerRequestHelper {

    public static ToolsManagerRequest buildOne() {
        return ToolsManagerRequest.builder()
                .name("name")
                .category("category")
                .description("description")
                .instructions("instructions")
                .price(100.0)
                .availableFrom(LocalDate.now())
                .availableUntil(LocalDate.now().plusWeeks(1))
                .build();
    }
}
