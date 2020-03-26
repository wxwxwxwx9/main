package seedu.address.commons.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/**
 * Class containing DateTime parser.
 */
public class DateTimeUtil {

    private static DateTimeFormatter getDateTimeFormatter(int index, int defaultYear) {
        switch (index) {
        case 0:
            return new DateTimeFormatterBuilder()
                    .appendPattern("yyyy M d")
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .toFormatter();
        case 1:
            return new DateTimeFormatterBuilder()
                    .appendPattern("d M yyyy")
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .toFormatter();
        case 2:
            return new DateTimeFormatterBuilder()
                    .appendPattern("d M")
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.YEAR, defaultYear)
                    .toFormatter();
        default:
            // default should never be triggered but just in case.
            assert false;
            return DateTimeFormatter.ofPattern("d/M/yyyy");
        }
    }

    /**
     * Parse the input string as a LocalDateTime if possible.
     *
     * @param dateTime String to be parsed.
     * @return The parsed string as a LocalDateTime.
     * @throws DateTimeParseException if string unable to be parsed.
     */
    public static LocalDate parseDate(String dateTime) throws DateTimeParseException {
        int currentYear = LocalDate.now().getYear();
        dateTime = dateTime.replaceAll("[\\\\/\\-]+", " ");
        for (int i = 0; i < 3; i++) {
            try {
                return LocalDate.parse(dateTime, getDateTimeFormatter(i, currentYear));
            } catch (DateTimeParseException e) {
                // It's fine if DateTimeParseException is thrown now, it's only used to check format of date.
                // DateTimeParseException will be thrown if all cases fail.
            }
        }
        // This throws a DateTimeParseException
        return LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("d/M/yyyy"));
    }
}
