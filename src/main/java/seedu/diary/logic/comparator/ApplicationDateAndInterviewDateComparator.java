package seedu.diary.logic.comparator;

import java.time.LocalDate;
import java.util.Comparator;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Comparator for sorting InternshipApplication by application date and interview date.
 */
public class ApplicationDateAndInterviewDateComparator implements Comparator<InternshipApplication> {

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        LocalDate dateOfInternship1 = internship1.getEarliestApplicationOrInterviewDate().fullApplicationDate;
        LocalDate dateOfInternship2 = internship2.getEarliestApplicationOrInterviewDate().fullApplicationDate;

        return dateOfInternship1.compareTo(dateOfInternship2);
    }

    @Override
    public String toString() {
        return "Earliest application/ interview date";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ApplicationDateAndInterviewDateComparator;
    }
}
