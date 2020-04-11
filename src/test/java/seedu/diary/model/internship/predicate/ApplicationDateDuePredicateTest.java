package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.testutil.InternshipApplicationBuilder;

class ApplicationDateDuePredicateTest {
    @Test
    public void equals() {
        ApplicationDateDuePredicate applicationDateDuePredicate = new ApplicationDateDuePredicate();

        // same object -> returns true
        assertEquals(applicationDateDuePredicate, applicationDateDuePredicate);

        // different types -> returns false
        assertNotEquals(1, applicationDateDuePredicate);

        // null -> returns false
        assertNotEquals(null, applicationDateDuePredicate);
    }

    @Test
    public void test_futureApplicationDateIsWithin7Days_returnsTrue() {
        ApplicationDateDuePredicate predicate = new ApplicationDateDuePredicate();

        // application date is same as current date
        LocalDate currentDate = LocalDate.now();
        ApplicationDate newDate = new ApplicationDate(currentDate);
        assertTrue(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));

        // application date is within 7 days from current date
        LocalDate laterDate = LocalDate.now().plus(4, ChronoUnit.DAYS);
        newDate = new ApplicationDate(laterDate.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));
        LocalDate sevenDaysFromCurrentDate = LocalDate.now().plus(7, ChronoUnit.DAYS);
        newDate = new ApplicationDate(sevenDaysFromCurrentDate.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));
    }

    @Test
    public void test_applicationDateHasAlreadyPassed_returnsFalse() {
        ApplicationDateDuePredicate predicate = new ApplicationDateDuePredicate();

        // application date is before current date
        LocalDate pastDate = LocalDate.now().minus(4, ChronoUnit.DAYS);
        ApplicationDate newDate = new ApplicationDate(pastDate.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));
        LocalDate sevenDaysBeforeCurrent = LocalDate.now().minus(7, ChronoUnit.DAYS);
        newDate = new ApplicationDate(sevenDaysBeforeCurrent.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));
        LocalDate moreThanOneWeekBeforeCurrent = LocalDate.now().minus(12, ChronoUnit.DAYS);
        newDate = new ApplicationDate(moreThanOneWeekBeforeCurrent.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));
    }

    @Test
    public void test_futureApplicationDateIsNotWithin7Days_returnsFalse() {
        ApplicationDateDuePredicate predicate = new ApplicationDateDuePredicate();

        // application date is more than 7 days past current date
        LocalDate laterDate = LocalDate.now().plus(10, ChronoUnit.DAYS);
        ApplicationDate newDate = new ApplicationDate(laterDate.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));
    }
}
