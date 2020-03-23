package seedu.address.logic.comparator;

import java.util.Comparator;

import seedu.address.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by date.
 */
public class ApplicationDateComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getApplicationDate().compareTo(internship2.getApplicationDate());
    }

    @Override
    public boolean equals(Object other) {
        // equal only if todayDate is same
        return other instanceof ApplicationDateComparator;
    }
}
