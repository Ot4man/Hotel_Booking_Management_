package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {

    public static BigDecimal calculateTotal(
            BigDecimal pricePerNight,
            long numberOfNights
    ) {
        return pricePerNight
                .multiply(BigDecimal.valueOf(numberOfNights))
                .setScale(2, RoundingMode.HALF_UP);
    }
}

