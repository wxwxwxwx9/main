package seedu.address.logic.comparator;

import java.time.LocalDate;
import java.util.Comparator;

import seedu.address.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by application date and interview date.
 */
public class ApplicationDateAndInterviewDateComparator implements Comparator<InternshipApplication> {
    private final LocalDate todayDate;

    public ApplicationDateAndInterviewDateComparator() {
        todayDate = LocalDate.now();
    }

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        LocalDate dateOfInternship1 = internship1.getEarliestApplicationOrInterviewDate().fullApplicationDate;
        LocalDate dateOfInternship2 = internship2.getEarliestApplicationOrInterviewDate().fullApplicationDate;

        return dateOfInternship1.compareTo(dateOfInternship2);
    }

    @Override
    public String toString() {
        return "Not Sorted";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ApplicationDateAndInterviewDateComparator;
    }
}
