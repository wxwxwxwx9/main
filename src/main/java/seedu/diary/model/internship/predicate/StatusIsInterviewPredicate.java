package seedu.diary.model.internship.predicate;

import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.status.Status;

/**
 * Tests that a {@code InternshipApplication}'s {@code Status} is INTERVIEW.
 */
public class StatusIsInterviewPredicate implements Predicate<InternshipApplication> {

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        return internshipApplication.getStatus() == Status.INTERVIEW;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StatusIsInterviewPredicate); // instanceof handles nulls
    }
}
