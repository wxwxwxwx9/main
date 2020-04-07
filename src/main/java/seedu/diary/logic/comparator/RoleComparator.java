package seedu.diary.logic.comparator;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Comparator;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by Role.
 */
public class RoleComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getRole().compareTo(internship2.getRole());
    }

    @Override
    public Comparator<InternshipApplication> reversed() {
        return new ReversedComparator(this);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RoleComparator; // All RoleComparator are the same.
    }

    @Override
    public String toString() {
        return PREFIX_ROLE.getPrefix();
    }
}
