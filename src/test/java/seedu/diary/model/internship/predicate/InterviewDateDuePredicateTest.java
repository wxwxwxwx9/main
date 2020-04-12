package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.testutil.InternshipApplicationBuilder;

class InterviewDateDuePredicateTest {
    @Test
    public void equals() {
        InterviewDateDuePredicate interviewDateDuePredicate = new InterviewDateDuePredicate();

        // same object -> returns true
        assertTrue(interviewDateDuePredicate.equals(interviewDateDuePredicate));

        // different types -> returns false
        assertFalse(interviewDateDuePredicate.equals(1));

        // null -> returns false
        assertNotEquals(null, interviewDateDuePredicate);
    }

    @Test
    public void test_futureInterviewDateIsWithin7Days_returnsTrue() {
        InterviewDateDuePredicate predicate = new InterviewDateDuePredicate();

        // interview date is same as current date
        InternshipApplicationBuilder internshipApplicationTestCurrent = new InternshipApplicationBuilder();
        LocalDate currentDate = LocalDate.now();
        Interview newInterviewTestCurrent = Interview.createInterview(true, new ApplicationDate(currentDate),
            new Address("123 Stevens Road"));
        internshipApplicationTestCurrent.withInterview(newInterviewTestCurrent);
        assertTrue(predicate.test(internshipApplicationTestCurrent.buildWithInterviews()));

        // interview date is within 7 days from current date
        InternshipApplicationBuilder internshipApplicationTestWithin = new InternshipApplicationBuilder();
        LocalDate laterDate = currentDate.plus(4, ChronoUnit.DAYS);
        Interview newInterviewTestWithin = Interview.createInterview(true, new ApplicationDate(laterDate),
            new Address("123 Stevens Road"));
        internshipApplicationTestWithin.withInterview(newInterviewTestWithin);
        assertTrue(predicate.test(internshipApplicationTestWithin.buildWithInterviews()));
    }

    @Test
    public void test_interviewDateHasPassed_returnsFalse() {
        InterviewDateDuePredicate predicate = new InterviewDateDuePredicate();

        // interview date date is before current date
        InternshipApplicationBuilder internshipApplicationTestPast = new InternshipApplicationBuilder();
        LocalDate pastDate = LocalDate.now().minus(4, ChronoUnit.DAYS);
        Interview newInterviewTestPast = Interview.createInterview(true, new ApplicationDate(pastDate),
            new Address("123 Stevens Road"));
        internshipApplicationTestPast.withInterview(newInterviewTestPast);
        assertFalse(predicate.test(internshipApplicationTestPast.buildWithInterviews()));
    }

    @Test
    public void test_interviewDateIsNotWithin7Days_returnsFalse() {
        InterviewDateDuePredicate predicate = new InterviewDateDuePredicate();

        // interview date is more than 7 days past current date
        InternshipApplicationBuilder internshipApplicationTestMore = new InternshipApplicationBuilder();
        LocalDate laterDate = LocalDate.now().plus(10, ChronoUnit.DAYS);
        Interview newInterviewTestMore = Interview.createInterview(true, new ApplicationDate(laterDate),
            new Address("123 Stevens Road"));
        internshipApplicationTestMore.withInterview(newInterviewTestMore);
        assertFalse(predicate.test(internshipApplicationTestMore.buildWithInterviews()));
    }
}
