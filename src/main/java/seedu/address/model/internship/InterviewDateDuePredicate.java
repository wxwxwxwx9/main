package seedu.address.model.internship;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.internship.interview.Interview;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Tests that a {@code InternshipApplication}'s {@code Application Date} is within 7 days from current date.
 */
public class InterviewDateDuePredicate implements Predicate<InternshipApplication> {
    private final LocalDate currentDate;

    public InterviewDateDuePredicate() {
        this.currentDate = LocalDate.now();
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        boolean hasAtLeastOneInterviewDue = false;
        ArrayList<Interview> interviews = internshipApplication.getInterviews();
        for (Interview interview: interviews){
            LocalDate interviewDate = interview.getInterviewDate();
            // count days between every interview in the internship application and current date
            Duration duration = Duration.between(currentDate, interviewDate);
            if ((interviewDate.compareTo(currentDate) > 0) && (duration.toDays() <= 7)) {
                hasAtLeastOneInterviewDue = true;
                break;
            }
        }
        return hasAtLeastOneInterviewDue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewDateDuePredicate // instanceof handles nulls
                && currentDate.equals(((InterviewDateDuePredicate) other).currentDate)); // state check
    }
}
