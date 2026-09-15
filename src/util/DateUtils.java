package util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static long calculteNights(LocalDate checkIn, LocalDate checkout) {
        return ChronoUnit.DAYS.between(checkIn, checkout);
    }

    public static boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);

    }

    public static boolean areValidDates(LocalDate checkIn, LocalDate checkout) {
        return checkIn.isBefore(checkout);
    }

}