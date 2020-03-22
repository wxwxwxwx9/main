package seedu.address.logic.comparator;

import org.junit.jupiter.api.Test;
import seedu.address.model.internship.InternshipApplication;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationDateThenInterviewDateComparatorTest {

    @Test
    public void equals() {
        Comparator<InternshipApplication> applicationDateThenInterviewDateComparator =
                new ApplicationDateThenInterviewDateComparator();

        // same object -> returns true
        assertEquals(applicationDateThenInterviewDateComparator, applicationDateThenInterviewDateComparator);

        // different types -> returns false
        assertNotEquals(1, applicationDateThenInterviewDateComparator);

        // null -> returns false
        assertNotEquals(null, applicationDateThenInterviewDateComparator);
    }
}