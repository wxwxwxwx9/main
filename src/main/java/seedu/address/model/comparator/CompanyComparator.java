package seedu.address.model.comparator;

import seedu.address.model.internship.InternshipApplication;

import java.util.Comparator;

public class CompanyComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getCompany().compareTo(internship2.getCompany());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CompanyComparator; // All company comparators are the same.
    }
}
