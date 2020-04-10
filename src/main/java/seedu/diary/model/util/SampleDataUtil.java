package seedu.diary.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.diary.model.InternshipDiary;
import seedu.diary.model.ReadOnlyInternshipDiary;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.model.status.Status;

/**
 * Contains utility methods for populating {@code InternshipDiary} with sample data.
 */
public class SampleDataUtil {
    private static final long DAYS_TO_MINUS = 1;

    private static Interview[] sampleInterviews = {Interview.createOnlineInterview(
        new ApplicationDate(LocalDate.now().minusDays(DAYS_TO_MINUS))), Interview.createInterview(false,
        new ApplicationDate(LocalDate.now().minusDays(DAYS_TO_MINUS)), new Address("123 Kent Ridge Road"))};

    public static InternshipApplication[] getSampleInternshipApplications() {
        return new InternshipApplication[]{
            new InternshipApplication(new Company("Google"), new Role("Software Developer"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Phone("87438807"),
                new Email("google.recruit@example.com"), new ApplicationDate(LocalDate.now().minusDays(DAYS_TO_MINUS)),
                new Priority(8), Status.APPLIED),
            new InternshipApplication(new Company("Facebook"), new Role("Software Engineer"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Phone("99272758"),
                new Email("facebook.recruit@example.com"),
                new ApplicationDate(LocalDate.now().minusDays(DAYS_TO_MINUS)),
                new Priority(8), Status.APPLIED),
            new InternshipApplication(new Company("DSO"), new Role("Data Analyst"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Phone("93210283"),
                new Email("dso.recruit@example.com"), new ApplicationDate(LocalDate.now().minusDays(DAYS_TO_MINUS)),
                new Priority(8), Status.INTERVIEW, false, new ArrayList<>(Arrays.asList(sampleInterviews))),
            new InternshipApplication(new Company("Shopee"), new Role("Data Science"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Phone("91031282"),
                new Email("shopee.recruit@example.com"), new ApplicationDate(LocalDate.now()),
                new Priority(10), Status.WISHLIST),
            new InternshipApplication(new Company("DSTA"), new Role("Software Engineer"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Phone("92492021"),
                new Email("dsta.recruit@example.com"), new ApplicationDate(LocalDate.now().minusDays(DAYS_TO_MINUS)),
                new Priority(2), Status.REJECTED, Status.INTERVIEW, new ArrayList<>(Arrays.asList(sampleInterviews))),
            new InternshipApplication(new Company("Singtel"), new Role("Software Engineer"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Phone("92624417"),
                new Email("singtel.recruit@example.com"), new ApplicationDate(LocalDate.now().minusDays(DAYS_TO_MINUS)),
                new Priority(2), Status.OFFERED)
        };
    }

    public static ReadOnlyInternshipDiary getSampleInternshipDiary() {
        InternshipDiary sampleDiary = new InternshipDiary();
        for (InternshipApplication sampleApplication : getSampleInternshipApplications()) {
            sampleDiary.loadInternshipApplication(sampleApplication);
        }
        return sampleDiary;
    }

}
