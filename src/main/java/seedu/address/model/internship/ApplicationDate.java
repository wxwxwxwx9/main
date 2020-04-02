package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.util.DateTimeUtil;

/**
 * Represents a InternshipApplication's application date in the internship diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidApplicationDate(String)}
 */
public class ApplicationDate implements Comparable<ApplicationDate> {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in the form: DD MM YYYY";

    // Default Pattern
    public static final String DATE_PATTERN = "dd MM yyyy";
    // To print pattern
    public static final String DATE_PATTERN_TO_PRINT = "dd MMM yyyy";

    public final LocalDate fullApplicationDate;

    /**
     * Constructs a {@code ApplicationDate}.
     *
     * @param date A valid date.
     */
    public ApplicationDate(String date) {
        requireNonNull(date);
        checkArgument(isValidApplicationDate(date), MESSAGE_CONSTRAINTS);
        fullApplicationDate = DateTimeUtil.parseDate(date);
    }

    /**
     * Constructs a {@code ApplicationDate}.
     *
     * @param date A valid date of type LocalDate.
     */
    public ApplicationDate(LocalDate date) {
        requireNonNull(date);
        fullApplicationDate = date;
    }

    /**
     * Returns true if a given string is a valid application date.
     */
    public static boolean isValidApplicationDate(String test) {
        try {
            //simple parse test
            DateTimeUtil.parseDate(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Prints the date in the print format provided.
     */
    public String printDate() {
        return fullApplicationDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN_TO_PRINT));
    }

    @Override
    public String toString() {
        return fullApplicationDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApplicationDate // instanceof handles nulls
                && fullApplicationDate.equals(((ApplicationDate) other).fullApplicationDate)); // state check
    }

    @Override
    public int compareTo(ApplicationDate other) {
        return fullApplicationDate.compareTo(other.fullApplicationDate);
    }

    @Override
    public int hashCode() {
        return String.valueOf(fullApplicationDate).hashCode();
    }

}
