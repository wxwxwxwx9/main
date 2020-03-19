package seedu.address.logic.comparator;

import seedu.address.model.internship.InternshipApplication;

import java.util.Comparator;

public class PriorityComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getPriority().compareTo(internship2.getPriority());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PriorityComparator; // All PriorityComparator are the same.
    }
}
