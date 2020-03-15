package seedu.address.model.util;

import java.time.LocalDate;

import seedu.address.model.InternshipDiary;
import seedu.address.model.ReadOnlyInternshipDiary;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.internship.Company;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.Phone;
import seedu.address.model.internship.Priority;
import seedu.address.model.internship.Role;
import seedu.address.model.status.Status;

/**
 * Contains utility methods for populating {@code InternshipDiary} with sample data.
 */
public class SampleDataUtil {

    public static InternshipApplication[] getSampleInternshipApplications() {
        return new InternshipApplication[] {
            new InternshipApplication(new Company("Google"), new Role("Software Developer"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Phone("87438807"),
                new Email("google.recruit@example.com"), new ApplicationDate(LocalDate.now()),
                    new Priority(8), Status.ACTIVE),
            new InternshipApplication(new Company("Facebook"), new Role("Software Engineer"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Phone("99272758"),
                new Email("facebook.recruit@example.com"), new ApplicationDate(LocalDate.now()),
                    new Priority(8), Status.ACTIVE),
            new InternshipApplication(new Company("DSO"), new Role("Data Analyst"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Phone("93210283"),
                new Email("dso.recruit@example.com"), new ApplicationDate(LocalDate.now()),
                    new Priority(8), Status.ACTIVE),
            new InternshipApplication(new Company("Shopee"), new Role("Data Science"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Phone("91031282"),
                new Email("shopee.recruit@example.com"), new ApplicationDate(LocalDate.now()),
                    new Priority(10), Status.PLAN_TO_APPLY),
            new InternshipApplication(new Company("DSTA"), new Role("Software Engineer"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Phone("92492021"),
                new Email("dsta.recruit@example.com"), new ApplicationDate(LocalDate.now()),
                    new Priority(2), Status.INACTIVE),
            new InternshipApplication(new Company("Singtel"), new Role("Software Engineer"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Phone("92624417"),
                new Email("singtel.recruit@example.com"), new ApplicationDate(LocalDate.now()),
                    new Priority(2), Status.INACTIVE)
        };
    }

    public static ReadOnlyInternshipDiary getSampleInternshipDiary() {
        InternshipDiary sampleDiary = new InternshipDiary();
        for (InternshipApplication sampleApplication : getSampleInternshipApplications()) {
            sampleDiary.addInternshipApplication(sampleApplication);
        }
        return sampleDiary;
    }

    // old code
    //    /**
    //     * Returns a tag set containing the list of strings given.
    //     */
    //    public static Set<Tag> getTagSet(String... strings) {
    //        return Arrays.stream(strings)
    //                .map(Tag::new)
    //                .collect(Collectors.toSet());
    //    }

}
