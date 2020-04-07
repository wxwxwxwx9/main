package seedu.diary.logic.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;

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
public class CompanyComparatorTest {
    @Test
    public void equals() {
        CompanyComparator companyComparator1 = new CompanyComparator();
        CompanyComparator companyComparator2 = new CompanyComparator();

        // same object -> returns true
        assertEquals(companyComparator1, companyComparator1);

        // same kind of object -> returns true
        assertEquals(companyComparator1, companyComparator2);

        // Reverse is the same -> return true
        assertEquals(companyComparator1.reversed(), companyComparator2.reversed());
    }

    @Test
    public void compare_internshipApplication_correct() {
        CompanyComparator companyComparator = new CompanyComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;
        InternshipApplication google1 = new InternshipApplicationBuilder(google).withCompany("A").build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google).withCompany("C").build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook).withCompany("A").build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook).withCompany("D").build();

        // same object
        assertEquals(0, companyComparator.compare(google, google));

        // only same company
        assertEquals(0, companyComparator.compare(google1, facebook1));

        // only company is different
        assertTrue(companyComparator.compare(google1, google2) < 0);
        assertTrue(companyComparator.compare(google2, google1) > 0);

        // only everything is different
        assertTrue(companyComparator.compare(google1, facebook2) < 0);
        assertTrue(companyComparator.compare(facebook2, google2) > 0);

        // everything reversed is different
        Comparator<InternshipApplication> reversed = companyComparator.reversed();
        assertTrue(reversed.compare(google1, facebook2) > 0);
        assertTrue(reversed.compare(facebook2, google2) < 0);
    }

    @Test
    public void compare_unsortedList_listSorted() {
        CompanyComparator companyComparator = new CompanyComparator();
        InternshipApplication google = TypicalInternshipApplications.GOOGLE;
        InternshipApplication facebook = TypicalInternshipApplications.FACEBOOK;
        InternshipApplication google1 = new InternshipApplicationBuilder(google).withCompany("A").build();
        InternshipApplication google2 = new InternshipApplicationBuilder(google).withCompany("C").build();
        InternshipApplication facebook1 = new InternshipApplicationBuilder(facebook).withCompany("B").build();
        InternshipApplication facebook2 = new InternshipApplicationBuilder(facebook).withCompany("D").build();

        ArrayList<InternshipApplication> unsorted = new ArrayList<>();
        Collections.addAll(unsorted, google2, google1, facebook1, facebook2);
        unsorted.sort(companyComparator);

        ArrayList<InternshipApplication> sorted = new ArrayList<>();
        Collections.addAll(sorted, google1, facebook1, google2, facebook2);

        assertEquals(sorted, unsorted);
    }

    @Test
    public void toString_returnsPrefix() {
        assertEquals(new CompanyComparator().toString(), PREFIX_COMPANY.getPrefix());
    }
}
