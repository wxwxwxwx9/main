package seedu.address.logic.comparator;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.Comparator;

import seedu.address.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by Priority.
 */
public class PriorityComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getPriority().compareTo(internship2.getPriority());
    }

    @Override
    public Comparator<InternshipApplication> reversed() {
        return new ReversedComparator(this);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PriorityComparator; // All PriorityComparator are the same.
    }

    @Override
    public String toString() {
        return PREFIX_PRIORITY.getPrefix();
    }
}
