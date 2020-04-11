package seedu.diary.model.internship.predicate;

import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code isArchived} is false.
 */
public class IsNotArchivedPredicate implements Predicate<InternshipApplication> {

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        return !internshipApplication.isArchived();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof IsNotArchivedPredicate); // instanceof handles nulls
    }
}

