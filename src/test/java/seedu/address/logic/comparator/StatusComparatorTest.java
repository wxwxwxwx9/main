package seedu.address.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.TypicalInternshipApplications;

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
}
