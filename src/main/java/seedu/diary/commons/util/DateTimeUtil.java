package seedu.diary.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * Class containing DateTime parser.
 */
public class DateTimeUtil {

    private static DateTimeFormatter getDateTimeFormatter(int index, int defaultYear) {
        switch (index) {
        case 0:
            return new DateTimeFormatterBuilder()
                .appendPattern("uuuu M d")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        case 1:
            return new DateTimeFormatterBuilder()
                .appendPattern("d M uuuu")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        case 2:
            return new DateTimeFormatterBuilder()
                .parseDefaulting(ChronoField.YEAR, defaultYear)
                .appendPattern("d M")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        case 3:
            return new DateTimeFormatterBuilder()
                .appendPattern("d MMM uuuu")
                .toFormatter()
                .withLocale(Locale.ENGLISH)
                .withResolverStyle(ResolverStyle.STRICT);
        default:
            // default should never be triggered but just in case.
            throw new IllegalStateException("DateTimeUtil illegal case.");
        }
    }

    /**
     * Parse the input string as a LocalDateTime if possible.
     *
     * @param dateString String to be parsed.
     * @return The parsed string as a LocalDateTime.
     * @throws DateTimeParseException if string unable to be parsed.
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        requireNonNull(dateString);
        int currentYear = LocalDate.now().getYear();
        String formattedDateString = dateString.replaceAll("[\\\\/\\- ]+", " ");
        for (int i = 0; i < 4; i++) {
            try {
                return LocalDate.parse(formattedDateString, getDateTimeFormatter(i, currentYear));
            } catch (DateTimeParseException e) {
                // It's fine if DateTimeParseException is thrown now, it's only used to check format of date.
                // DateTimeParseException will be thrown if all cases fail.
            }
        }
        // This throws a DateTimeParseException
        return LocalDate.parse(formattedDateString, getDateTimeFormatter(1, currentYear));
    }
}
