package ferramong.toolsmanager.exceptions;

public class CannotUpdateRentedToolException extends Exception {

    public CannotUpdateRentedToolException(int toolId) {
        super("Rented tools cannot be updated: toolId=" + toolId);
    }
}
