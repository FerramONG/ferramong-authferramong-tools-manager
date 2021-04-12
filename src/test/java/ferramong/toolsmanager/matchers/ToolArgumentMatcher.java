package ferramong.toolsmanager.matchers;

import ferramong.toolsmanager.entities.Tool;
import org.mockito.ArgumentMatcher;

public class ToolArgumentMatcher implements ArgumentMatcher<Tool> {
    private Tool compareTo;

    public ToolArgumentMatcher(Tool compareTo) {
        this.compareTo = compareTo;
    }

    @Override
    public boolean matches(Tool argument) {
        return argument.getId().equals(compareTo.getId());
    }
}
