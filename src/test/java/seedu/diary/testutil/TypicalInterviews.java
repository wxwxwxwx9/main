package seedu.diary.testutil;

import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_NUS;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_DATE_NUS;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_DATE_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_IS_ONLINE_NUS;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_IS_ONLINE_ONLINE;

import seedu.diary.model.internship.interview.Interview;

/**
 * A utility class consisting of a list of {@code Interview} objects to be used in tests.
 */
public class TypicalInterviews {

    public static final Interview CENTRAL_LIBRARY = new InterviewBuilder()
        .withAddress("215 Bras Basah").withDate("05 05 2020").withIsOnline(false).build();
    public static final Interview ORCHARD_TOWER = new InterviewBuilder()
        .withAddress("Orchard Towers 12-555").withDate("20 10 2020").withIsOnline(false).build();

    public static final Interview NUS = new InterviewBuilder().withAddress(VALID_ADDRESS_NUS)
        .withDate(VALID_DATE_NUS).withIsOnline(VALID_IS_ONLINE_NUS).build();
    public static final Interview ONLINE = new InterviewBuilder().withAddress(VALID_ADDRESS_ONLINE)
        .withDate(VALID_DATE_ONLINE).withIsOnline(VALID_IS_ONLINE_ONLINE).build();
}
