package seedu.diary.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.testutil.InternshipApplicationBuilder;

class ApplicationDateAndInterviewDateComparatorTest {

    @Test
    public void compare() {
        LocalDate currentDate = LocalDate.now();
        assertEquals(currentDate.compareTo(currentDate), 0);
        InternshipApplicationBuilder internship1 = new InternshipApplicationBuilder();
        internship1.withApplicationDate(new ApplicationDate(currentDate));
        InternshipApplicationBuilder internship2 = new InternshipApplicationBuilder();
        internship2.withApplicationDate(new ApplicationDate(currentDate.plus(1, ChronoUnit.DAYS)));
        InternshipApplicationBuilder internship3 = new InternshipApplicationBuilder();
        internship3.withApplicationDate(new ApplicationDate(currentDate.plus(1, ChronoUnit.DAYS)));

        // an application's application date compared to itself -> returns 0
        assertEquals(internship1.getApplicationDate().compareTo(internship1.getApplicationDate()), 0);
        // comparing two applications with different application dates -> does not return 0
        assertNotEquals(internship1.getApplicationDate().compareTo(internship2.getApplicationDate()), 0);
        // comparing internship1(with earlier application date) and internship2 (with later application date)
        // -> returns negative int
        assertEquals(internship1.getApplicationDate().compareTo(internship2.getApplicationDate()), -1);

        // with same application date, comparing internship2(with earlier 'earliest interview date') and
        // internship3(with later 'earliest interview date') --> returns negative int
        Interview newInterviewForInternship2 = Interview.createInterview(true,
            new ApplicationDate(currentDate.plus(12, ChronoUnit.DAYS)),
            new Address("123 Stevens Road"));
        Interview anotherInterviewForInternship2 = Interview.createInterview(true,
            new ApplicationDate(currentDate.plus(25, ChronoUnit.DAYS)),
            new Address("123 Stevens Road"));
        internship2.withInterview(newInterviewForInternship2);
        internship2.withInterview(anotherInterviewForInternship2);
        Interview newInterviewForInternship3 = Interview.createInterview(true,
            new ApplicationDate(currentDate.plus(16, ChronoUnit.DAYS)),
            new Address("123 Stevens Road"));
        internship3.withInterview(newInterviewForInternship3);
        Optional<Interview> earliestInterviewForInternship2 = internship2.getEarliestInterview(currentDate);
        assertTrue(earliestInterviewForInternship2.isPresent());
        Optional<Interview> earliestInterviewForInternship3 = internship3.getEarliestInterview(currentDate);
        assertTrue(earliestInterviewForInternship3.isPresent());
        LocalDate earliestInterviewDateForInternship2 = earliestInterviewForInternship2.get().getInterviewDate();
        LocalDate earliestInterviewDateForInternship3 = earliestInterviewForInternship3.get().getInterviewDate();
        assertEquals(earliestInterviewDateForInternship2.compareTo(earliestInterviewDateForInternship3), -4);
    }

    @Test
    public void equals() {
        Comparator<InternshipApplication> applicationDateAndInterviewDateComparator =
            new ApplicationDateAndInterviewDateComparator();

        // same object -> returns true
        assertEquals(applicationDateAndInterviewDateComparator, applicationDateAndInterviewDateComparator);

        // different types -> returns false
        assertNotEquals(1, applicationDateAndInterviewDateComparator);

        // null -> returns false
        assertNotEquals(null, applicationDateAndInterviewDateComparator);
    }
}
