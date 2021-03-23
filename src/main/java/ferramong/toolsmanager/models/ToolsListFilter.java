package ferramong.toolsmanager.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data @AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ToolsListFilter {

    private String toolName;
    private String dwellerName;

    public static class ToolsListFilterBuilder {
        public ToolsListFilter build() {
            if (isBlank(toolName) && isBlank(dwellerName)) {
                throw new IllegalArgumentException("Properties 'toolName' and 'dwellerName' cannot both be blank");
            }

            return new ToolsListFilter(toolName, dwellerName);
        }
    }
}
