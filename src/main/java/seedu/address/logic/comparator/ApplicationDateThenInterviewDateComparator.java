package seedu.address.logic.comparator;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

/**
 * Comparator for sorting InternshipApplication by date.
 */
public class ApplicationDateThenInterviewDateComparator implements Comparator<InternshipApplication> {
    private final LocalDate todayDate;

    public ApplicationDateThenInterviewDateComparator() {
        todayDate = LocalDate.now();
    }

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        LocalDate dateOfInternship1 = internship1.getApplicationDate().fullApplicationDate;
        LocalDate dateOfInternship2 = internship2.getApplicationDate().fullApplicationDate;

        if (dateOfInternship1.compareTo(dateOfInternship2) == 0) { // two applications have same application date
            Optional<Interview> earliestInterviewForInternship1 = internship1.getEarliestInterview(todayDate);
            Optional<Interview> earliestInterviewForInternship2 = internship2.getEarliestInterview(todayDate);
            if (earliestInterviewForInternship1.isPresent() && earliestInterviewForInternship2.isPresent()) {
                LocalDate earliestInterviewDtForInternship1 = earliestInterviewForInternship1.get().getInterviewDate();
                LocalDate earliestInterviewDtForInternship2 = earliestInterviewForInternship2.get().getInterviewDate();
                return earliestInterviewDtForInternship1.compareTo(earliestInterviewDtForInternship2);
            }
            return dateOfInternship1.compareTo(dateOfInternship2);
        }

        return dateOfInternship1.compareTo(dateOfInternship2);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ApplicationDateThenInterviewDateComparator;
    }

    @Override
    public String toString() {
        return "Application date, then interview date";
    }
}
