package seedu.address.model.internship.predicate;

import org.junit.jupiter.api.Test;
import seedu.address.model.status.Status;
import seedu.address.testutil.InternshipApplicationBuilder;

import static org.junit.jupiter.api.Assertions.*;

class StatusIsWishlistPredicateTest {
    @Test
    public void equals() {
        StatusIsInterviewPredicate predicate = new StatusIsInterviewPredicate();

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_statusIsInterview_returnsTrue() {
        StatusIsWishlistPredicate predicate = new StatusIsWishlistPredicate();

        assertTrue(predicate.test(new InternshipApplicationBuilder().withStatus(Status.WISHLIST).build()));
    }

    @Test
    public void test_statusIsNotInterview_returnsFalse() {
        StatusIsWishlistPredicate predicate = new StatusIsWishlistPredicate();

        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.APPLIED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.INTERVIEW).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.OFFERED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.GHOSTED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.REJECTED).build()));
    }

}