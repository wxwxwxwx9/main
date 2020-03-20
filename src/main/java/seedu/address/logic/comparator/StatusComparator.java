package seedu.address.logic.comparator;

import java.util.Comparator;

import seedu.address.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by Status.
 */
public class StatusComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getStatus().compareTo(internship2.getStatus());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StatusComparator; // All StatusComparator are the same.
    }
}
