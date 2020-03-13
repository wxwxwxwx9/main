package seedu.address.model.internship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidApplicationDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ApplicationDate(invalidApplicationDate));
    }

    @Test
    public void isValidCompany() {
        // null application date
        assertThrows(NullPointerException.class, () -> ApplicationDate.isValidApplicationDate(null));

        // invalid application date
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only
        assertFalse(Company.isValidCompany("^")); // only non-alphanumeric characters
        assertFalse(Company.isValidCompany("20-01-2020")); // contains non-alphanumeric characters

        // valid application date
        assertTrue(Company.isValidCompany("01 01 2020")); // first day of year
        assertTrue(Company.isValidCompany("31 12 2020")); // last day of year
    }
}
