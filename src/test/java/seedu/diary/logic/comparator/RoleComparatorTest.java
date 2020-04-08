package seedu.diary.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.testutil.InternshipApplicationBuilder;
import seedu.diary.testutil.TypicalInternshipApplications;

/**
 * Comparator for sorting InternshipApplication by companies in lexicographical order.
 */
public class RoleComparatorTest {
    @Test
    public void equals() {
        RoleComparator roleComparator1 = new RoleComparator();
        RoleComparator roleComparator2 = new RoleComparator();

        // same object -> returns true
        assertEquals(roleComparator1, roleComparator1);

        // same kind of object -> returns true
        assertEquals(roleComparator1, roleComparator2);

        // Reverse is the same -> return true
        assertEquals(roleComparator1.reversed(), roleComparator2.reversed());
    }

    @Test
    public void compare_internshipApplication_correct() {
        RoleComparator roleComparator = new RoleComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;
        InternshipApplication google1 = new InternshipApplicationBuilder(google).withRole("A").build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google).withRole("C").build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook).withRole("A").build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook).withRole("D").build();

        // same object
        assertEquals(0, roleComparator.compare(google, google));

        // only same company
        assertEquals(0, roleComparator.compare(google1, facebook1));

        // only company is different
        assertTrue(roleComparator.compare(google1, google2) < 0);
        assertTrue(roleComparator.compare(google2, google1) > 0);

        // only everything is different
        assertTrue(roleComparator.compare(google1, facebook2) < 0);
        assertTrue(roleComparator.compare(facebook2, google2) > 0);

        // everything reversed is different
        Comparator<InternshipApplication> reversed = roleComparator.reversed();
        assertTrue(reversed.compare(google1, facebook2) > 0);
        assertTrue(reversed.compare(facebook2, google2) < 0);
    }

    @Test
    public void compare_unsortedList_listSorted() {
        RoleComparator roleComparator = new RoleComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;
        InternshipApplication google1 = new InternshipApplicationBuilder(google).withRole("A").build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google).withRole("C").build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook).withRole("B").build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook).withRole("D").build();

        ArrayList<InternshipApplication> unsorted = new ArrayList<>();
        Collections.addAll(unsorted, google2, google1, facebook1, facebook2);
        unsorted.sort(roleComparator);

        ArrayList<InternshipApplication> sorted = new ArrayList<>();
        Collections.addAll(sorted, google1, facebook1, google2, facebook2);

        assertEquals(sorted, unsorted);
    }

    @Test
    public void toString_returnsPrefix() {
        assertEquals(new RoleComparator().toString(), PREFIX_ROLE.getPrefix());
    }
}
