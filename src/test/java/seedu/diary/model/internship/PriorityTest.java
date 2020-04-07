package seedu.diary.model.internship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("91")); // more than 10
        assertFalse(Priority.isValidPriority("-1")); // less than 0
        assertFalse(Priority.isValidPriority("1p0")); // alphabets within digits
        assertFalse(Priority.isValidPriority("1 0")); // spaces within digits
        assertFalse(Priority.isValidPriority(91)); // more than 10
        assertFalse(Priority.isValidPriority(-1)); // less than 0

        // valid priority
        assertTrue(Priority.isValidPriority("0")); // lowest
        assertTrue(Priority.isValidPriority("5")); // middle
        assertTrue(Priority.isValidPriority("10")); // largest
        assertTrue(Priority.isValidPriority(0)); // lowest
        assertTrue(Priority.isValidPriority(5)); // middle
        assertTrue(Priority.isValidPriority(10)); // largest
    }
}
