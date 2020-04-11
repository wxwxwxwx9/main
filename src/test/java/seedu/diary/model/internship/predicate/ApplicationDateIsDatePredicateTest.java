package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InternshipApplicationBuilder;

public class ApplicationDateIsDatePredicateTest {

    @Test
    public void equals() {
        LocalDate firstPredicateDate = LocalDate.of(2020, 02, 01);
        LocalDate secondPredicateDate = LocalDate.of(2021, 03, 02);

        ApplicationDateIsDatePredicate firstPredicate =
            new ApplicationDateIsDatePredicate(firstPredicateDate);
        ApplicationDateIsDatePredicate secondPredicate =
            new ApplicationDateIsDatePredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ApplicationDateIsDatePredicate firstPredicateCopy =
            new ApplicationDateIsDatePredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different dates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_applicationDateContainsNumbers_returnsTrue() {
        ApplicationDateIsDatePredicate predicate =
            new ApplicationDateIsDatePredicate(LocalDate.of(2020, 02, 01));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withApplicationDate("01 02 2020").build()));
    }

    @Test
    public void test_applicationDateDoesNotContainNumbers_returnsFalse() {
        // Non-matching date
        ApplicationDateIsDatePredicate predicate = new ApplicationDateIsDatePredicate(LocalDate.of(2020, 02, 01));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withApplicationDate("02 02 2020").build()));
    }

    @Test
    public void isNull_nullDate_returnsTrue() {
        ApplicationDateIsDatePredicate predicate = new ApplicationDateIsDatePredicate((LocalDate) null);
        assertTrue(predicate.isNull());
    }

    @Test
    public void isNull_nonNullDate_returnsFalse() {
        ApplicationDateIsDatePredicate predicate = new ApplicationDateIsDatePredicate(LocalDate.of(2020, 02, 01));
        assertFalse(predicate.isNull());
    }
}
