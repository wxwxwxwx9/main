package seedu.address.logic.commands;

import seedu.address.logic.comparator.DateComparator;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_INTERNSHIPS;

/**
 * Lists all internship applications in the internship diary to the user.
 */
public class ArchivalCommand extends Command {

    public static final String COMMAND_WORD = "archival";

    public static final String MESSAGE_SUCCESS = "Listed all archived internship applications";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredInternshipApplicationList(new DateComparator()); // to reset any existing sorting
        model.updateFilteredInternshipApplicationList(PREDICATE_SHOW_ARCHIVED_INTERNSHIPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
