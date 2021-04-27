package ferramong.toolsmanager.exceptions;

public class CannotDeleteRentedToolException extends Exception {

    public CannotDeleteRentedToolException(int toolId) {
        super("Rented tools cannot be deleted: toolId=" + toolId);
    }
}
