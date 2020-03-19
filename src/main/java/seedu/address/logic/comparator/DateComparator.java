package seedu.address.logic.comparator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

/**
 * Comparator for sorting InternshipApplication by date.
 */
public class DateComparator implements Comparator<InternshipApplication> {
    private final LocalDate todayDate;

    public DateComparator() {
        todayDate = LocalDate.now();
    }

    /**
     * Allows a custom date to be injected.
     */
    public DateComparator(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    @Override
    public int compare(InternshipApplication internship1, InternshipApplication internship2) {
        LocalDate dateToCompare1;
        Optional<Interview> earliestInterview1 = internship1.getEarliestInterview(todayDate);
        if (earliestInterview1.isPresent()) {
            dateToCompare1 = earliestInterview1.get().getInterviewDate();
        } else {
            dateToCompare1 = internship1.getApplicationDate().fullApplicationDate;
        }

        LocalDate dateToCompare2;
        Optional<Interview> earliestInterview2 = internship2.getEarliestInterview(todayDate);
        if (earliestInterview2.isPresent()) {
            dateToCompare2 = earliestInterview2.get().getInterviewDate();
        } else {
            dateToCompare2 = internship2.getApplicationDate().fullApplicationDate;
        }

        return dateToCompare1.compareTo(dateToCompare2);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DateComparator; // All DateComparators are the same.
    }
}
