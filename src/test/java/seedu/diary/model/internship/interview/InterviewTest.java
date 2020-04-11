package seedu.diary.model.internship.interview;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.diary.testutil.TypicalInterviews.CENTRAL_LIBRARY;
import static seedu.diary.testutil.TypicalInterviews.ONLINE;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InterviewBuilder;

/**
 * Tests integration between OfflineInterview and OnlineInterview.
 */
public class InterviewTest {

    @Test
    public void createInterview_invalidArgs_throwsNullPointerException() {
        Interview interview = CENTRAL_LIBRARY;
        // Null date
        assertThrows(NullPointerException.class, () -> Interview.createInterview(false, null,
            interview.getInterviewAddress()));
        // Null address
        assertThrows(NullPointerException.class, () -> Interview.createInterview(false,
            interview.getDate(), null));
    }

    @Test
    public void createOnlineInterview_validArgs_success() {
        Interview interview = ONLINE;
        Interview onlineInterview = Interview.createOnlineInterview(interview.getDate());
        assertTrue(ONLINE.equals(onlineInterview));
    }

    @Test
    public void equals() {

        // same values -> returns true
        Interview clbCopy = new InterviewBuilder(CENTRAL_LIBRARY).build();
        assertTrue(CENTRAL_LIBRARY.equals(clbCopy));

        // same object -> returns true
        assertTrue(CENTRAL_LIBRARY.equals(CENTRAL_LIBRARY));

        // null -> returns false
        assertFalse(CENTRAL_LIBRARY.equals(null));

        // different type -> returns false
        assertFalse(CENTRAL_LIBRARY.equals(5));

        // different isOnline -> returns false
        assertFalse(CENTRAL_LIBRARY.equals(ONLINE));

        // different diary -> returns false
        Interview editedClb = new InterviewBuilder(CENTRAL_LIBRARY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(CENTRAL_LIBRARY.equals(editedClb));

        // different date -> returns false
        editedClb = new InterviewBuilder(CENTRAL_LIBRARY).withDate(VALID_DATE_AMY).build();
        assertFalse(CENTRAL_LIBRARY.equals(editedClb));

        // different isOnline -> returns false
        editedClb = new InterviewBuilder(CENTRAL_LIBRARY).withIsOnline(true).build();
        assertFalse(CENTRAL_LIBRARY.equals(editedClb));
    }
}
