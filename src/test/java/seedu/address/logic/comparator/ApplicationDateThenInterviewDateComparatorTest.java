package seedu.address.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;
import seedu.address.testutil.InternshipApplicationBuilder;

class ApplicationDateThenInterviewDateComparatorTest {

    @Test
    public void compare() {
        InternshipApplicationBuilder internship1 = new InternshipApplicationBuilder();
        internship1.withApplicationDate(new ApplicationDate(LocalDate.now()));
        InternshipApplicationBuilder internship2 = new InternshipApplicationBuilder();
        internship2.withApplicationDate(new ApplicationDate(LocalDate.now().plus(1, ChronoUnit.DAYS)));
        InternshipApplicationBuilder internship3 = new InternshipApplicationBuilder();
        internship3.withApplicationDate(new ApplicationDate(LocalDate.now().plus(1, ChronoUnit.DAYS)));

        // an application's application date compared to itself -> returns 0
        assertEquals(internship1.getApplicationDate().compareTo(internship1.getApplicationDate()), 0);
        // comparing two applications with different application dates -> does not return 0
        assertNotEquals(internship1.getApplicationDate().compareTo(internship2.getApplicationDate()), 0);
        // comparing internship1(with earlier application date) and internship2 (with later application date)
        // -> returns negative int
        assertEquals(internship1.getApplicationDate().compareTo(internship2.getApplicationDate()), -1);

        // with same application date, comparing internship2(with earlier 'earliest interview date') and
        // internship3(with later 'earliest interview date') --> returns negative int
        Interview newInterviewForInternship2 = new Interview(true,
                new ApplicationDate(LocalDate.now().plus(12, ChronoUnit.DAYS)),
                new Address("123 Stevens Road"));
        Interview anotherInterviewForInternship2 = new Interview(true,
                new ApplicationDate(LocalDate.now().plus(25, ChronoUnit.DAYS)),
                new Address("123 Stevens Road"));
        internship2.withInterview(newInterviewForInternship2);
        internship2.withInterview(anotherInterviewForInternship2);
        Interview newInterviewForInternship3 = new Interview(true,
                new ApplicationDate(LocalDate.now().plus(16, ChronoUnit.DAYS)),
                new Address("123 Stevens Road"));
        internship3.withInterview(newInterviewForInternship3);
        Optional<Interview> earliestInterviewForInternship2 = internship2.getEarliestInterview(LocalDate.now());
        assertTrue(earliestInterviewForInternship2.isPresent());
        Optional<Interview> earliestInterviewForInternship3 = internship3.getEarliestInterview(LocalDate.now());
        assertTrue(earliestInterviewForInternship3.isPresent());
        LocalDate earliestInterviewDateForInternship2 = earliestInterviewForInternship2.get().getInterviewDate();
        LocalDate earliestInterviewDateForInternship3 = earliestInterviewForInternship3.get().getInterviewDate();
        assertEquals(earliestInterviewDateForInternship2.compareTo(earliestInterviewDateForInternship3), -4);
    }

    @Test
    public void equals() {
        Comparator<InternshipApplication> applicationDateThenInterviewDateComparator =
                new ApplicationDateThenInterviewDateComparator();

        // same object -> returns true
        assertEquals(applicationDateThenInterviewDateComparator, applicationDateThenInterviewDateComparator);

        // different types -> returns false
        assertNotEquals(1, applicationDateThenInterviewDateComparator);

        // null -> returns false
        assertNotEquals(null, applicationDateThenInterviewDateComparator);
    }
}
