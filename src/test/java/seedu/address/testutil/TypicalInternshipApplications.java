package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import seedu.address.model.InternshipDiary;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;

/**
 * A utility class containing a list of {@code InternshipApplication} objects to be used in tests.
 */
public class TypicalInternshipApplications {

    public static final InternshipApplication GOOGLE = new InternshipApplicationBuilder().withCompany("Google")
            .withRole("Software Engineer").withAddress("1600 Amphitheatre Parkway")
            .withPhone("94351253").withEmail("larry@google.com")
            .withStatus(Status.APPLICATION_DONE)
            .withApplicationDate(new Date(2019, 12, 01))
            .withPriority(10)
            .build();
    public static final InternshipApplication FACEBOOK = new InternshipApplicationBuilder().withCompany("Facebook")
            .withRole("Product Management").withAddress("1 Hacker Way, Menlo Park")
            .withPhone("99751354").withEmail("mark@google.com")
            .withStatus(Status.REJECTED)
            .withApplicationDate(new Date(2009, 12, 01))
            .withPriority(8)
            .build();

    private TypicalInternshipApplications() {} // prevents instantiation

    /**
     * Returns an {@code InternshipDiary} with all the typical internship applications.
     */
    public static InternshipDiary getTypicalInternshipDiary() {
        InternshipDiary diary = new InternshipDiary();
        for (InternshipApplication application : getTypicalInternshipApplications()) {
            diary.addInternshipApplication(application);
        }
        return diary;
    }

    public static List<InternshipApplication> getTypicalInternshipApplications() {
        return new ArrayList<>(Arrays.asList(GOOGLE, FACEBOOK));
    }
}
