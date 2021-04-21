package ferramong.toolsmanager.exceptions;

public class RentalNotFoundException extends Exception {

    public RentalNotFoundException(int toolId) {
        super("Rental has not been found: toolId=" + toolId);
    }
}
