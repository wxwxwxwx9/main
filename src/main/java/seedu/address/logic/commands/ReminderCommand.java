package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.internship.ApplicationDateDuePredicate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.InterviewDateDuePredicate;

/**
 * Lists all internship applications in the internship diary that are due or have interview dates in 7 days.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows applications that have are due or have interview"
            + "dates in 7 days.\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed applications are due or have interviews in 7 days.";

    private final ApplicationDateDuePredicate appDatePredicate;
    private final InterviewDateDuePredicate interviewDatePredicate;

    public ReminderCommand() {
        this.appDatePredicate = new ApplicationDateDuePredicate();
        this.interviewDatePredicate = new InterviewDateDuePredicate();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Predicate<InternshipApplication>> predicates = new ArrayList<>();
        predicates.add(appDatePredicate);
        predicates.add(interviewDatePredicate);
        Predicate<InternshipApplication> predicate = predicates.stream().reduce(x -> true, Predicate::or);
        model.updateFilteredInternshipApplicationList(appDatePredicate); //todo: to update after interviews can be added
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand // instanceof handles nulls
                && appDatePredicate.equals(((ReminderCommand) other).appDatePredicate)
                && interviewDatePredicate.equals(((ReminderCommand) other).interviewDatePredicate)); // state check
    }

}
