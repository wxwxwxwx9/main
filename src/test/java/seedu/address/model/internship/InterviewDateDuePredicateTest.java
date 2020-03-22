package seedu.address.model.internship;

import org.junit.jupiter.api.Test;
import seedu.address.model.internship.interview.Interview;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.InterviewBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class InterviewDateDuePredicateTest {
    @Test
    public void equals() {
        InterviewDateDuePredicate interviewDateDuePredicate = new InterviewDateDuePredicate();

        // same object -> returns true
        assertTrue(interviewDateDuePredicate.equals(interviewDateDuePredicate));

        // different types -> returns false
        assertFalse(interviewDateDuePredicate.equals(1));

        // null -> returns false
        assertFalse(interviewDateDuePredicate.equals(null));
    }

    @Test
    public void test_futureInterviewDateIsWithin7Days_returnsTrue() {
        InterviewDateDuePredicate predicate = new InterviewDateDuePredicate();

        // interview date is same as current date
        InternshipApplicationBuilder internshipApplication_testCurrent = new InternshipApplicationBuilder();
        LocalDate currentDate = LocalDate.now();
        String newDate = currentDate.format(DateTimeFormatter.ofPattern("dd MM YYYY"));
        Interview newInterview_testCurrent = new Interview(true, new ApplicationDate(newDate),
                new Address("123 Stevens Road"));
        internshipApplication_testCurrent.withInterview(newInterview_testCurrent);
        assertTrue(predicate.test(internshipApplication_testCurrent.buildWithInterviews()));

        // interview date is within 7 days from current date
        InternshipApplicationBuilder internshipApplication_testWithin = new InternshipApplicationBuilder();
        LocalDate laterDate = LocalDate.now().plus(4, ChronoUnit.DAYS);
        newDate = laterDate.format(DateTimeFormatter.ofPattern("dd MM YYYY"));
        Interview newInterview_testWithin = new Interview(true, new ApplicationDate(newDate),
                new Address("123 Stevens Road"));
        internshipApplication_testWithin.withInterview(newInterview_testWithin);
        assertTrue(predicate.test(internshipApplication_testWithin.buildWithInterviews()));
    }

    @Test
    public void test_interviewDateHasPassed_returnsFalse() {
        InterviewDateDuePredicate predicate = new InterviewDateDuePredicate();

        // interview date date is before current date
        InternshipApplicationBuilder internshipApplication_testPast = new InternshipApplicationBuilder();
        LocalDate pastDate = LocalDate.now().minus(4, ChronoUnit.DAYS);
        String newDate = pastDate.format(DateTimeFormatter.ofPattern("dd MM YYYY"));
        Interview newInterview_testPast = new Interview(true, new ApplicationDate(newDate),
                new Address("123 Stevens Road"));
        internshipApplication_testPast.withInterview(newInterview_testPast);
        assertFalse(predicate.test(internshipApplication_testPast.buildWithInterviews()));
    }

    @Test
    public void test_interviewDateIsNotWithin7Days_returnsFalse() {
        InterviewDateDuePredicate predicate = new InterviewDateDuePredicate();

        // interview date is more than 7 days past current date
        InternshipApplicationBuilder internshipApplication_testMore = new InternshipApplicationBuilder();
        LocalDate laterDate = LocalDate.now().plus(10, ChronoUnit.DAYS);
        String newDate = laterDate.format(DateTimeFormatter.ofPattern("dd MM YYYY"));
        Interview newInterview_testMore = new Interview(true, new ApplicationDate(newDate),
                new Address("123 Stevens Road"));
        internshipApplication_testMore.withInterview(newInterview_testMore);
        assertFalse(predicate.test(internshipApplication_testMore.buildWithInterviews()));
    }
}