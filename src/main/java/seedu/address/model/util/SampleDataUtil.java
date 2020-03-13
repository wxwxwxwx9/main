package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//import seedu.address.model.AddressBook;
import seedu.address.model.InternshipDiary;
import seedu.address.model.ReadOnlyInternshipDiary;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    // old code
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                     new Address("Blk 47 Tampines Street 20, #17-35"),
                     getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                     new Address("Blk 45 Aljunied Street 85, #11-31"),
                     getTagSet("colleagues"))
        };
    }

    /**
    public static InternshipApplication[] getSampleInternshipApplications() {
        return new InternshipApplication[] {
                new InternshipApplication(new Company("Google"), new Role("Software Developer"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Phone("87438807"),
                        new Email("google.recruit@example.com"), new Date(), new Priority(8), Status.ACTIVE),
                new InternshipApplication(new Company("Facebook"), new Role("Software Engineer"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Phone("99272758"),
                        new Email("facebook.recruit@example.com"), new Date(), new Priority(8), Status.ACTIVE),
                new InternshipApplication(new Company("DSO"),  new Role("Data Analyst"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Phone("93210283"),
                        new Email("dso.recruit@example.com"), new Date(), new Priority(8), Status.ACTIVE),
                new InternshipApplication(new Company("Shopee"), new Role("Data Science"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Phone("91031282"),
                        new Email("shopee.recruit@example.com"), new Date(), new Priority(10), Status.PLAN_TO_APPLY),
                new InternshipApplication(new Company("DSTA"), new Role("Software Engineer"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Phone("92492021"),
                        new Email("dsta.recruit@example.com"), new Date(), new Priority(2), Status.INACTIVE),
                new InternshipApplication(new Company("Singtel"), new Role("Software Engineer"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),  new Phone("92624417"),
                        new Email("singtel.recruit@example.com"), new Date(), new Priority(2), Status.INACTIVE)
        };
    }**/

    // old code
//    public static ReadOnlyAddressBook getSampleAddressBook() {
//        AddressBook sampleAb = new AddressBook();
//        for (Person samplePerson : getSamplePersons()) {
//            sampleAb.addPerson(samplePerson);
//        }
//        return sampleAb;
//    }

    public static ReadOnlyInternshipDiary getSampleInternshipDiary() {
        InternshipDiary sampleDiary = new InternshipDiary();
        // for (InternshipApplication sampleApplication : getSampleInternshipDiary()) {
        //  sampleDiary.addInternshipApplication(sampleApplication);
        // }
        return sampleDiary;
    }

    // old code
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
