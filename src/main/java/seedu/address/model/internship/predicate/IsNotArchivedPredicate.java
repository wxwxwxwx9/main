package seedu.address.model.internship.predicate;

import java.util.function.Predicate;

import seedu.address.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code isArchived} is false.
 */
public class IsNotArchivedPredicate implements Predicate<InternshipApplication> {

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (internshipApplication.isArchived()) {
            System.out.println("Returns false");
            return false;
        } else {
            System.out.println("Returns true");
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsNotArchivedPredicate); // instanceof handles nulls
    }
}

