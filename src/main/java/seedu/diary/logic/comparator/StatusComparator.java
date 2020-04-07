package seedu.diary.logic.comparator;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Comparator;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by Status.
 */
public class StatusComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getStatus().compareTo(internship2.getStatus());
    }

    @Override
    public Comparator<InternshipApplication> reversed() {
        return new ReversedComparator(this);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StatusComparator; // All StatusComparator are the same.
    }

    @Override
    public String toString() {
        return PREFIX_STATUS.getPrefix();
    }
}
