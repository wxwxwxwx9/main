package seedu.address.model.internship;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.InternshipApplicationBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationDateDuePredicateTest {
    @Test
    public void equals() {
        ApplicationDateDuePredicate applicationDateDuePredicate = new ApplicationDateDuePredicate();

        // same object -> returns true
        assertTrue(applicationDateDuePredicate.equals(applicationDateDuePredicate));

        // different types -> returns false
        assertFalse(applicationDateDuePredicate.equals(1));

        // null -> returns false
        assertFalse(applicationDateDuePredicate.equals(null));
    }

    @Test
    public void test_futureApplicationDateIsWithin7Days_returnsTrue() {
        ApplicationDateDuePredicate predicate = new ApplicationDateDuePredicate();

        // application date is same as current date
        LocalDate currentDate = LocalDate.now();
        ApplicationDate newDate = new ApplicationDate(currentDate.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
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
    public void test_applicationDateHasPassed_returnsFalse() {
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
    public void test_applicationDateIsNotWithin7Days_returnsFalse() {
        ApplicationDateDuePredicate predicate = new ApplicationDateDuePredicate();

        // application date is more than 7 days past current date
        LocalDate laterDate = LocalDate.now().plus(10, ChronoUnit.DAYS);
        ApplicationDate newDate = new ApplicationDate(laterDate.format(DateTimeFormatter.ofPattern("dd MM YYYY")));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withApplicationDate(newDate).build()));
    }
}