package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Comparator;

import seedu.diary.commons.core.Messages;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;


/**
 * Finds and lists all internship applications in internship diary
 * whose company name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String REVERSE_KEYWORD = "reverse";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sorts current list by given parameter.\n"
        + "Parameters: [" + REVERSE_KEYWORD + "] KEYWORD\n"
        + "Available sorts:"
        + "Company: " + PREFIX_COMPANY + ", "
        + "Role: " + PREFIX_ROLE + ", "
        + "Date: " + PREFIX_DATE + ", "
        + "Priority: " + PREFIX_PRIORITY + ", "
        + "Status: " + PREFIX_STATUS + ".\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_COMPANY + "\n"
        + "Remarks: sort order is detailed in the User Guide.";

    private final Comparator<InternshipApplication> comparator;

    public SortCommand(Comparator<InternshipApplication> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredInternshipApplicationList(comparator);
        return new CommandResult(
            String.format(Messages.MESSAGE_INTERNSHIP_LISTED_OVERVIEW,
                model.getFilteredInternshipApplicationList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortCommand // instanceof handles nulls
            && comparator.equals(((SortCommand) other).comparator)); // state check
    }
}
