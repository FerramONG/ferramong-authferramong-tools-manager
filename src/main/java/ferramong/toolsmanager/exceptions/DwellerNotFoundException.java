package ferramong.toolsmanager.exceptions;

public class DwellerNotFoundException extends Exception {

    public DwellerNotFoundException(String dwellerName) {
        super("Dweller has not been found: dwellerName=" + dwellerName);
    }

    public DwellerNotFoundException(int dwellerId) {
        super("Dweller has not been found: dwellerId=" + dwellerId);
    }
}
