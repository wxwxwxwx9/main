package seedu.diary.logic.comparator;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Comparator;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by date.
 */
public class ApplicationDateComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getApplicationDate().compareTo(internship2.getApplicationDate());
    }

    @Override
    public Comparator<InternshipApplication> reversed() {
        return new ReversedComparator(this);
    }

    @Override
    public boolean equals(Object other) {
        // equal only if todayDate is same
        return other instanceof ApplicationDateComparator;
    }

    @Override
    public String toString() {
        return PREFIX_DATE.getPrefix();
    }
}
