package ferramong.toolsmanager.helpers;

import ferramong.toolsmanager.entities.Tool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ToolEntityHelper {

    public static Tool buildOne() {
        return buildOne(1);
    }

    public static Tool buildOne(Integer id) {
        return Tool.builder()
                .id(id)
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

    public static List<Tool> buildList() {
        List<Tool> tools = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tools.add(buildOne(i));
        }

        return tools;
    }
}
