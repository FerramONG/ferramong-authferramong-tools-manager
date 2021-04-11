package ferramong.toolsmanager.exceptions;

public class ToolNotFoundException extends Exception {

    public ToolNotFoundException(int toolId) {
        super("Tool has not been found: toolId=" + toolId);
    }
}
