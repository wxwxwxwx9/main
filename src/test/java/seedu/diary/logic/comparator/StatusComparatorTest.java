package seedu.diary.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.status.Status;
import seedu.diary.testutil.InternshipApplicationBuilder;
import seedu.diary.testutil.TypicalInternshipApplications;

/**
 * Comparator for sorting InternshipApplication by Status.
 */
public class StatusComparatorTest {

    @Test
    public void equals() {
        StatusComparator statusComparator1 = new StatusComparator();
        StatusComparator statusComparator2 = new StatusComparator();

        // same object -> returns true
        assertEquals(statusComparator1, statusComparator1);

        // same kind of object -> returns true
        assertEquals(statusComparator1, statusComparator2);

        // Reverse is the same -> return true
        assertEquals(statusComparator1.reversed(), statusComparator2.reversed());
    }

    @Test
    public void compare_internshipApplication_correct() {
        StatusComparator statusComparator = new StatusComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;

        InternshipApplication google1 = new InternshipApplicationBuilder(google)
            .withStatus(Status.WISHLIST).build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google)
            .withStatus(Status.APPLIED).build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook)
            .withStatus(Status.WISHLIST).build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook)
            .withStatus(Status.OFFERED).build();

        // same object
        assertEquals(0, statusComparator.compare(google, google));

        // only same status
        assertEquals(0, statusComparator.compare(google1, facebook1));

        // only status is different
        assertTrue(statusComparator.compare(google1, google2) < 0);
        assertTrue(statusComparator.compare(google2, google1) > 0);

        // everything is different
        assertTrue(statusComparator.compare(google1, facebook2) < 0);
        assertTrue(statusComparator.compare(facebook2, google2) > 0);

        // everything reversed is different
        Comparator<InternshipApplication> reversed = statusComparator.reversed();
        assertTrue(reversed.compare(google1, facebook2) > 0);
        assertTrue(reversed.compare(facebook2, google2) < 0);
    }

    @Test
    public void compare_unsortedList_listSorted() {
        StatusComparator statusComparator = new StatusComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;

        InternshipApplication google1 = new InternshipApplicationBuilder(google)
            .withStatus(Status.WISHLIST).build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google)
            .withStatus(Status.INTERVIEW).build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook)
            .withStatus(Status.APPLIED).build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook)
            .withStatus(Status.OFFERED).build();

        ArrayList<InternshipApplication> unsorted = new ArrayList<>();
        Collections.addAll(unsorted, google2, google1, facebook1, facebook2);
        unsorted.sort(statusComparator);

        ArrayList<InternshipApplication> sorted = new ArrayList<>();
        Collections.addAll(sorted, google1, facebook1, google2, facebook2);

        assertEquals(sorted, unsorted);
    }

    @Test
    public void toString_returnsPrefix() {
        assertEquals(new StatusComparator().toString(), PREFIX_STATUS.getPrefix());
    }
}
