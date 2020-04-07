package seedu.diary.model.internship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ApplicationDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String nullStringApplicationDate = null;
        LocalDate nullApplicationDate = null;
        assertThrows(NullPointerException.class, () -> new ApplicationDate(nullStringApplicationDate));
        assertThrows(NullPointerException.class, () -> new ApplicationDate(nullApplicationDate));
    }

    @Test
    public void constructor_invalidApplicationDate_throwsIllegalArgumentException() {
        String invalidApplicationDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ApplicationDate(invalidApplicationDate));
    }

    @Test
    public void isValidApplicationDate() {
        // null application date
        assertThrows(NullPointerException.class, () -> ApplicationDate.isValidApplicationDate(null));

        // invalid application date
        assertFalse(ApplicationDate.isValidApplicationDate("")); // empty string
        assertFalse(ApplicationDate.isValidApplicationDate(" ")); // spaces only
        assertFalse(ApplicationDate.isValidApplicationDate("^")); // only non-alphanumeric characters
        assertFalse(ApplicationDate.isValidApplicationDate("20^01-2020")); // contains non-alphanumeric characters

        // valid application date
        assertTrue(ApplicationDate.isValidApplicationDate("01 01 2020")); // first day of year
        assertTrue(Company.isValidCompany("31 12 2020")); // last day of year
    }
}
