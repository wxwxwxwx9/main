package seedu.diary.model.internship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.diary.testutil.TypicalInternshipApplications.FACEBOOK;
import static seedu.diary.testutil.TypicalInternshipApplications.GOOGLE;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InternshipApplicationBuilder;
import seedu.diary.testutil.InterviewBuilder;
import seedu.diary.testutil.TypicalInterviews;

public class InternshipApplicationTest {

    @Test
    public void setInterviews_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> FACEBOOK.setInterviews(null));
    }

    @Test
    public void isSameInternshipApplication() {

        // same object -> returns true
        assertTrue(GOOGLE.isSameInternshipApplication(GOOGLE));

        // null -> returns false
        assertFalse(GOOGLE.isSameInternshipApplication(null));

        // different phone and email -> returns false
        InternshipApplication editedGoogle =
            new InternshipApplicationBuilder(GOOGLE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(GOOGLE.isSameInternshipApplication(editedGoogle));

        // different company -> returns false
        editedGoogle = new InternshipApplicationBuilder(GOOGLE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(GOOGLE.isSameInternshipApplication(editedGoogle));

        // CHECK WITH THE TEAM IF WE ARE GOING FORWARD WITH THE CURRENT EQUALITY REQUIREMENTS
        // different priority or status -> returns true
        editedGoogle = new InternshipApplicationBuilder(GOOGLE)
            .withPriority(VALID_PRIORITY_BOB)
            .withStatus(VALID_STATUS_BOB)
            .build();
        assertTrue(GOOGLE.isSameInternshipApplication(editedGoogle));
    }

    @Test
    public void equals() {

        // same values -> returns true
        InternshipApplication googleCopy = new InternshipApplicationBuilder(GOOGLE).build();
        assertTrue(GOOGLE.equals(googleCopy));

        // same object -> returns true
        assertTrue(GOOGLE.equals(GOOGLE));

        // null -> returns false
        assertFalse(GOOGLE.equals(null));

        // different type -> returns false
        assertFalse(GOOGLE.equals(5));

        // different company -> returns false
        assertFalse(GOOGLE.equals(FACEBOOK));

        // different company -> returns false
        InternshipApplication editedGoogle =
            new InternshipApplicationBuilder(GOOGLE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different phone -> returns false
        editedGoogle = new InternshipApplicationBuilder(GOOGLE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different email -> returns false
        editedGoogle = new InternshipApplicationBuilder(GOOGLE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(GOOGLE.equals(editedGoogle));

        // different diary -> returns false
        editedGoogle = new InternshipApplicationBuilder(GOOGLE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(GOOGLE.equals(editedGoogle));

    }

    @Test
    public void getEarliestInterview_noInterviewsInApplication_returnsEmptyOptional() {
        LocalDate date = LocalDate.MIN;
        InternshipApplication internshipApplication = new InternshipApplicationBuilder().build();
        assertTrue(internshipApplication.getEarliestInterview(date).isEmpty());
    }

    @Test
    public void getEarliestInterview_allInterviewsExpired_returnsEmptyOptional() {
        LocalDate date = LocalDate.MAX;
        InternshipApplication internshipApplication = new InternshipApplicationBuilder()
            .withInterview(new InterviewBuilder().build())
            .withInterview(new InterviewBuilder(TypicalInterviews.CENTRAL_LIBRARY).build())
            .buildWithInterviews();

        assertTrue(internshipApplication.getEarliestInterview(date).isEmpty());
    }

    @Test
    public void getEarliestInterview_someInterviewsExpired_returnsSmallestNonExpiredDate() {
        LocalDate maxDate = LocalDate.MAX;
        InternshipApplication internshipApplication = new InternshipApplicationBuilder()
            .withInterview(new InterviewBuilder().build())
            .withInterview(new InterviewBuilder(TypicalInterviews.CENTRAL_LIBRARY).build())
            .withInterview(new InterviewBuilder().withDate(maxDate).build())
            .buildWithInterviews();

        assertTrue(internshipApplication.getEarliestInterview(maxDate).get().getInterviewDate().equals(maxDate));
    }

    @Test
    public void getEarliestInterview_allInterviewsValid_returnsSmallestDate() {
        LocalDate minDate = LocalDate.MIN;
        InternshipApplication internshipApplication = new InternshipApplicationBuilder()
            .withInterview(new InterviewBuilder().build())
            .withInterview(new InterviewBuilder(TypicalInterviews.CENTRAL_LIBRARY).build())
            .withInterview(new InterviewBuilder().withDate(minDate).build())
            .buildWithInterviews();

        assertTrue(internshipApplication.getEarliestInterview(minDate).get().getInterviewDate().equals(minDate));
    }

    @Test
    public void getEarliestApplicationOrInterviewDate_applicationDateExpiredInterviewDateValid_returnsInterviewDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate interviewDate = currentDate.plus(2, ChronoUnit.DAYS);
        InternshipApplication internshipApplication = new InternshipApplicationBuilder()
            .withApplicationDate("20 12 2019")
            .withInterview(new InterviewBuilder().withDate(interviewDate).build())
            .buildWithInterviews();
        ApplicationDate earliestDate = internshipApplication.getEarliestApplicationOrInterviewDate();
        assertTrue(earliestDate.equals(new ApplicationDate(interviewDate)));
    }

    @Test
    public void getEarliestApplicationOrInterviewDate_onlyInterviewDatesValid_returnsEarliestInterviewDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate interviewDate1 = currentDate.plus(2, ChronoUnit.DAYS);
        LocalDate interviewDate2 = currentDate.plus(5, ChronoUnit.DAYS);
        LocalDate interviewDate3 = currentDate.plus(1, ChronoUnit.DAYS);
        InternshipApplication internshipApplication = new InternshipApplicationBuilder()
            .withApplicationDate("20 12 2019")
            .withInterview(new InterviewBuilder().withDate(interviewDate1).build())
            .withInterview(new InterviewBuilder().withDate(interviewDate2).build())
            .withInterview(new InterviewBuilder().withDate(interviewDate3).build())
            .buildWithInterviews();
        ApplicationDate earliestDate = internshipApplication.getEarliestApplicationOrInterviewDate();
        assertTrue(earliestDate.equals(new ApplicationDate(interviewDate3)));
    }

    @Test
    public void getEarliestApplicationOrInterviewDate_allDatesValid_returnsEarliestApplicationOrInterviewDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate interviewDate1 = currentDate.plus(7, ChronoUnit.DAYS);
        InternshipApplication internshipApplication = new InternshipApplicationBuilder()
            .withApplicationDate(new ApplicationDate(currentDate))
            .withInterview(new InterviewBuilder().withDate(interviewDate1).build())
            .buildWithInterviews();
        ApplicationDate earliestDate = internshipApplication.getEarliestApplicationOrInterviewDate();
        assertTrue(earliestDate.equals(new ApplicationDate(currentDate)));
    }
}
