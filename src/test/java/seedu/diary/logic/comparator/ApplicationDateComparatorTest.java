package seedu.diary.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.testutil.InternshipApplicationBuilder;
import seedu.diary.testutil.TypicalInternshipApplications;

/**
 * Comparator for sorting InternshipApplication by companies in lexicographical order.
 */
public class ApplicationDateComparatorTest {
    private static final ApplicationDate date1 = new ApplicationDate(LocalDate.of(2019, 11, 1));
    private static final ApplicationDate date2 = new ApplicationDate(LocalDate.of(2019, 11, 10));
    private static final ApplicationDate date3 = new ApplicationDate(LocalDate.of(2019, 12, 5));
    private static final ApplicationDate date4 = new ApplicationDate(LocalDate.of(2019, 12, 10));

    @Test
    public void equals() {
        ApplicationDateComparator applicationDateComparator1 = new ApplicationDateComparator();
        ApplicationDateComparator applicationDateComparator2 = new ApplicationDateComparator();

        // same object -> returns true
        assertEquals(applicationDateComparator1, applicationDateComparator1);

        // same kind of object -> returns true
        assertEquals(applicationDateComparator1, applicationDateComparator2);

        // Reverse is the same -> return true
        assertEquals(applicationDateComparator1.reversed(), applicationDateComparator2.reversed());
    }

    @Test
    public void compare_internshipApplication_correct() {
        ApplicationDateComparator applicationDateComparator = new ApplicationDateComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;

        InternshipApplication google1 = new InternshipApplicationBuilder(google)
            .withApplicationDate(date1).build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google)
            .withApplicationDate(date2).build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook)
            .withApplicationDate(date1).build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook)
            .withApplicationDate(date4).build();

        // same object
        assertEquals(0, applicationDateComparator.compare(google, google));

        // only same applicationDate
        assertEquals(0, applicationDateComparator.compare(google1, facebook1));

        // only applicationDate is different
        assertTrue(applicationDateComparator.compare(google1, google2) < 0);
        assertTrue(applicationDateComparator.compare(google2, google1) > 0);

        // everything is different
        assertTrue(applicationDateComparator.compare(google1, facebook2) < 0);
        assertTrue(applicationDateComparator.compare(facebook2, google2) > 0);

        // everything reversed is different
        Comparator<InternshipApplication> reversed = applicationDateComparator.reversed();
        assertTrue(reversed.compare(google1, facebook2) > 0);
        assertTrue(reversed.compare(facebook2, google2) < 0);
    }

    @Test
    public void compare_unsortedList_listSorted() {
        ApplicationDateComparator applicationDateComparator = new ApplicationDateComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;

        InternshipApplication google1 = new InternshipApplicationBuilder(google)
            .withApplicationDate(date1).build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google)
            .withApplicationDate(date3).build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook)
            .withApplicationDate(date2).build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook)
            .withApplicationDate(date3).build();

        ArrayList<InternshipApplication> unsorted = new ArrayList<>();
        Collections.addAll(unsorted, google2, google1, facebook1, facebook2);
        unsorted.sort(applicationDateComparator);

        ArrayList<InternshipApplication> sorted = new ArrayList<>();
        Collections.addAll(sorted, google1, facebook1, google2, facebook2);

        assertEquals(sorted, unsorted);
    }

    @Test
    public void toString_returnsPrefix() {
        assertEquals(new ApplicationDateComparator().toString(), PREFIX_DATE.getPrefix());
    }
}
