package seedu.address.model.internship;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code InternshipApplication}'s {@code ApplicationDate} matches the date given.
 */
public class ApplicationDateIsDatePredicate implements Predicate<InternshipApplication> {
    private final LocalDate date;

    public ApplicationDateIsDatePredicate(LocalDate date) {
        this.date = date;
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
}
