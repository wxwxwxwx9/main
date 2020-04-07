package seedu.diary.logic.comparator;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;

import java.util.Comparator;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by companies in lexicographical order.
 */
public class CompanyComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        return internship1.getCompany().compareTo(internship2.getCompany());
    }

    @Override
    public Comparator<InternshipApplication> reversed() {
        return new ReversedComparator(this);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CompanyComparator; // All CompanyComparator are the same.
    }

    @Override
    public String toString() {
        return PREFIX_COMPANY.getPrefix();
    }
}
