package seedu.diary.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.diary.storage.JsonAdaptedInterview.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalInterviews.NUS;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.exceptions.IllegalValueException;
import seedu.diary.commons.util.BooleanUtil;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;

public class JsonAdaptedInterviewTest {
    private static final String INVALID_IS_ONLINE = "n";
    private static final String INVALID_ADDRESS = "  ";
    private static final String INVALID_DATE = "11002222";

    private static final String VALID_IS_ONLINE = "false";
    private static final String VALID_ADDRESS = "123 avenue road";
    private static final String VALID_DATE = "10 10 2010";

    @Test
    public void toModelType_validInterviewDetails_returnsInternship() throws Exception {
        JsonAdaptedInterview interview = new JsonAdaptedInterview(NUS);
        assertEquals(NUS, interview.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedInterview interview = new JsonAdaptedInterview(INVALID_ADDRESS,
            VALID_IS_ONLINE, VALID_DATE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedInterview interview = new JsonAdaptedInterview(null,
            VALID_IS_ONLINE, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedInterview interview = new JsonAdaptedInterview(VALID_ADDRESS,
            VALID_IS_ONLINE, INVALID_DATE);
        String expectedMessage = ApplicationDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedInterview interview = new JsonAdaptedInterview(VALID_ADDRESS,
            VALID_IS_ONLINE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ApplicationDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidIsOnline_throwsIllegalValueException() {
        JsonAdaptedInterview interview = new JsonAdaptedInterview(VALID_ADDRESS,
            INVALID_IS_ONLINE, VALID_DATE);
        String expectedMessage = BooleanUtil.INVALID_BOOLEAN;
        assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullIsOnline_throwsIllegalValueException() {
        JsonAdaptedInterview interview = new JsonAdaptedInterview(VALID_ADDRESS,
            null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }
}
