package seedu.address.model.internship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.testutil.TypicalInternshipApplications.FACEBOOK;
import static seedu.address.testutil.TypicalInternshipApplications.GOOGLE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.InterviewBuilder;
import seedu.address.testutil.TypicalInterviews;

public class InternshipApplicationTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        InternshipApplication ia = new InternshipApplicationBuilder().build();
        // No more tags -- check with team how else we can implement this test case
        // assertThrows(UnsupportedOperationException.class, () -> ia.getTags().remove(0));
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

        // different address -> returns false
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

}
