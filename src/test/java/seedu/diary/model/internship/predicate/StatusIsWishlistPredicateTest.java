package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.diary.model.status.Status;
import seedu.diary.testutil.InternshipApplicationBuilder;

class StatusIsWishlistPredicateTest {
    @Test
    public void equals() {
        StatusIsWishlistPredicate predicate = new StatusIsWishlistPredicate();

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertNotEquals(null, predicate);
    }

    @Test
    public void test_statusIsWishlist_returnsTrue() {
        StatusIsWishlistPredicate predicate = new StatusIsWishlistPredicate();

        assertTrue(predicate.test(new InternshipApplicationBuilder().withStatus(Status.WISHLIST).build()));
    }

    @Test
    public void test_statusIsNotWishlist_returnsFalse() {
        StatusIsWishlistPredicate predicate = new StatusIsWishlistPredicate();

        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.APPLIED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.INTERVIEW).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.OFFERED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.GHOSTED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.REJECTED).build()));
    }

}
