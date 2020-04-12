package seedu.diary.model.internship.predicate;

import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.status.Status;

/**
 * Tests that a {@code InternshipApplication}'s {@code Status} is WISHLIST.
 */
public class StatusIsWishlistPredicate implements Predicate<InternshipApplication> {

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        return internshipApplication.getStatus() == Status.WISHLIST;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StatusIsWishlistPredicate); // instanceof handles nulls
    }
}

