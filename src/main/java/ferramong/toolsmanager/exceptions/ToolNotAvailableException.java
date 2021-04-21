package ferramong.toolsmanager.exceptions;

import ferramong.toolsmanager.utils.LocalDateUtils;

import java.time.LocalDate;

public class ToolNotAvailableException extends Exception {

    public ToolNotAvailableException(int toolId, LocalDate availableUntil) {
        super("Tool " + toolId + " is not available after " + LocalDateUtils.toString(availableUntil));
    }
}
