package seedu.address.model.internship.predicate;

import org.junit.jupiter.api.Test;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.status.Status;
import seedu.address.testutil.InternshipApplicationBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class StatusIsInterviewPredicateTest {
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
        StatusIsInterviewPredicate predicate = new StatusIsInterviewPredicate();

        assertTrue(predicate.test(new InternshipApplicationBuilder().withStatus(Status.INTERVIEW).build()));
    }

    @Test
    public void test_statusIsNotInterview_returnsFalse() {
        StatusIsInterviewPredicate predicate = new StatusIsInterviewPredicate();

        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.WISHLIST).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.APPLIED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.OFFERED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.GHOSTED).build()));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus(Status.REJECTED).build()));
    }
}