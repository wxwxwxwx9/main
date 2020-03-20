package seedu.address.logic.comparator;

import java.util.Comparator;

import seedu.address.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by companies in lexicographical order.
 */
public class CompanyComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getCompany().compareTo(internship2.getCompany());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CompanyComparator; // All CompanyComparator are the same.
    }
}
