package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.comparator.ApplicationDateAndInterviewDateComparator;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.ApplicationDateDuePredicate;
import seedu.address.model.internship.predicate.CustomToStringPredicate;
import seedu.address.model.internship.predicate.InterviewDateDuePredicate;
import seedu.address.model.internship.predicate.IsNotArchivedPredicate;
import seedu.address.model.internship.predicate.StatusIsInterviewPredicate;
import seedu.address.model.internship.predicate.StatusIsWishlistPredicate;

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
        List<Predicate<InternshipApplication>> checkWishlistPredicate = new ArrayList<>();
        checkWishlistPredicate.add(appDateWithin7DaysPredicate);
        checkWishlistPredicate.add(statusIsWishlistPredicate);
        Predicate<InternshipApplication> wishlistPredicate = checkWishlistPredicate.stream()
                .reduce(x -> true, Predicate::and);

        InterviewDateDuePredicate interviewDateWithin7DaysPredicate = new InterviewDateDuePredicate();
        StatusIsInterviewPredicate statusIsInterviewPredicate = new StatusIsInterviewPredicate();
        List<Predicate<InternshipApplication>> checkInterviewPredicate = new ArrayList<>();
        checkInterviewPredicate.add(interviewDateWithin7DaysPredicate);
        checkInterviewPredicate.add(statusIsInterviewPredicate);
        Predicate<InternshipApplication> interviewPredicate = checkInterviewPredicate.stream()
                .reduce(x -> true, Predicate::and);

        List<Predicate<InternshipApplication>> checkDatePredicates = new ArrayList<>();
        checkDatePredicates.add(wishlistPredicate);
        checkDatePredicates.add(interviewPredicate);
        Predicate<InternshipApplication> datePredicate = checkDatePredicates.stream().reduce(x -> false, Predicate::or);

        IsNotArchivedPredicate isNotArchivedPredicate = new IsNotArchivedPredicate();
        List<Predicate<InternshipApplication>> dateAndNotArchivedPredicates = new ArrayList<>();
        dateAndNotArchivedPredicates.add(datePredicate);
        dateAndNotArchivedPredicates.add(isNotArchivedPredicate);
        Predicate<InternshipApplication> predicate = dateAndNotArchivedPredicates.stream()
                .reduce(x -> true, Predicate::and);

        model.updateFilteredInternshipApplicationList(new ApplicationDateAndInterviewDateComparator());
        Predicate<InternshipApplication> customPredicate = new CustomToStringPredicate<>(predicate,
            "Reminder");
        model.updateFilteredInternshipApplicationList(customPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReminderCommand); // instanceof handles nulls
    }

}
