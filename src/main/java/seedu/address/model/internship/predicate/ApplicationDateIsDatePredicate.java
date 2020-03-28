package seedu.address.model.internship.predicate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code ApplicationDate} matches the date given.
 */
public class ApplicationDateIsDatePredicate implements Predicate<InternshipApplication> {
    private final LocalDate date;

    public ApplicationDateIsDatePredicate(LocalDate date) {
        this.date = date;
    }

    public ApplicationDateIsDatePredicate(List<String> dateArr) throws ParseException {
        String dateStr = String.join(" ", dateArr);
        this.date = ParserUtil.parseApplicationDate(dateStr).fullApplicationDate;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (date == null) {
            return true;
        }

        return internshipApplication.getApplicationDate().fullApplicationDate.isEqual(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApplicationDateIsDatePredicate // instanceof handles nulls
                && date.isEqual(((ApplicationDateIsDatePredicate) other).date)); // state check
    }

    public boolean isNull() {
        return date == null;
    }

    @Override
    public String toString() {
        return PREFIX_DATE + date.toString();
    }
}
