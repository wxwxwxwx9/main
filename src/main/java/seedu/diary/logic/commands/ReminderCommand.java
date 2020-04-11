package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.diary.logic.comparator.ApplicationDateAndInterviewDateComparator;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.predicate.ApplicationDateDuePredicate;
import seedu.diary.model.internship.predicate.CustomToStringPredicate;
import seedu.diary.model.internship.predicate.InterviewDateDuePredicate;
import seedu.diary.model.internship.predicate.IsNotArchivedPredicate;
import seedu.diary.model.internship.predicate.StatusIsInterviewPredicate;
import seedu.diary.model.internship.predicate.StatusIsWishlistPredicate;

/**
 * Lists all internship applications in the internship diary that are due or have interview dates in 7 days.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows applications that have are due or have interview"
        + "dates in 7 days.\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed applications that are due or have interviews in 7 days.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ApplicationDateDuePredicate appDateWithin7DaysPredicate = new ApplicationDateDuePredicate();
        StatusIsWishlistPredicate statusIsWishlistPredicate = new StatusIsWishlistPredicate();
        Predicate<InternshipApplication> wishlistPredicate = appDateWithin7DaysPredicate.and(statusIsWishlistPredicate);

        InterviewDateDuePredicate interviewDateWithin7DaysPredicate = new InterviewDateDuePredicate();
        StatusIsInterviewPredicate statusIsInterviewPredicate = new StatusIsInterviewPredicate();
        Predicate<InternshipApplication> interviewPredicate = interviewDateWithin7DaysPredicate
            .and(statusIsInterviewPredicate);

        Predicate<InternshipApplication> datePredicate = wishlistPredicate.or(interviewPredicate);

        IsNotArchivedPredicate isNotArchivedPredicate = new IsNotArchivedPredicate();

        Predicate<InternshipApplication> predicate = isNotArchivedPredicate.and(datePredicate);

        Predicate<InternshipApplication> customPredicate = new CustomToStringPredicate<>(predicate,
            "Reminder");
        model.updateFilteredInternshipApplicationList(customPredicate);
        model.updateFilteredInternshipApplicationList(new ApplicationDateAndInterviewDateComparator());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReminderCommand); // instanceof handles nulls
    }

}
