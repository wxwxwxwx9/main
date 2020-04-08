package seedu.diary.model.internship.predicate;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.interview.Interview;

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
        List<Interview> interviews = internshipApplication.getInterviews();
        for (Interview interview : interviews) {
            LocalDate interviewDate = interview.getInterviewDate();
            // count days between every interview in the internship application and current date
            Period duration = Period.between(currentDate, interviewDate);
            if ((interviewDate.compareTo(currentDate) >= 0) && (duration.getDays() <= 7)) {
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
