package seedu.address.logic.comparator;

import seedu.address.model.internship.InternshipApplication;

import java.util.Comparator;

public class DateComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getApplicationDate().compareTo(internship2.getApplicationDate());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DateComparator; // All DateComparators are the same.
    }
}
