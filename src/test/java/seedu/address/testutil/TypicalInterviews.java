package seedu.address.testutil;

import seedu.address.model.internship.interview.Interview;

/**
 * A utility class consisting of a list of {@code Interview} objects to be used in tests.
 */
public class TypicalInterviews {

    public static final Interview CENTRAL_LIBRARY = new InterviewBuilder()
            .withAddress("215 Bras Basah").withDate("05 03 2015").withIsOnline(false).build();
    public static final Interview ONLINE = new InterviewBuilder()
            .withAddress(Interview.ADDRESS_NOT_APPLICABLE).withDate("10 12 2018").withIsOnline(true).build();
    public static final Interview ORCHARD_TOWER = new InterviewBuilder()
            .withAddress("Orchard Towers 12-555").withDate("20 01 2020").withIsOnline(false).build();

}
