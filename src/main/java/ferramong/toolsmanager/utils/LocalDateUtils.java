package ferramong.toolsmanager.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateUtils {

    public static String toString(LocalDate localDate) {
        if (nonNull(localDate)) {
            return String.format("%d/%d/%d",
                    localDate.getYear(),
                    localDate.getMonthValue(),
                    localDate.getDayOfMonth());
        }

        return "";
    }
}
