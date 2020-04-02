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

/**
 * Lists all internship applications in the internship diary that are due or have interview dates in 7 days.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows applications that have are due or have interview"
        + "dates in 7 days.\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed applications are due or have interviews in 7 days.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ApplicationDateDuePredicate appDatePredicate = new ApplicationDateDuePredicate();
        InterviewDateDuePredicate interviewDatePredicate = new InterviewDateDuePredicate();
        List<Predicate<InternshipApplication>> predicates = new ArrayList<>();
        predicates.add(appDatePredicate);
        predicates.add(interviewDatePredicate);
        Predicate<InternshipApplication> predicate = predicates.stream().reduce(x -> false, Predicate::or);
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
