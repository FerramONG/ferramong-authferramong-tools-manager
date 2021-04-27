package ferramong.toolsmanager.exceptions;

import ferramong.toolsmanager.dto.Payment;

import javax.validation.constraints.NotNull;

import static java.lang.String.format;

public class NotEnoughCreditoolsException extends Exception {

    public NotEnoughCreditoolsException(@NotNull Payment payment) {
        super(format("Dweller does not have enough creditools: dwellerId=%s, value=%s",
                payment.getIdDweller(), payment.getValue()));
    }
}
