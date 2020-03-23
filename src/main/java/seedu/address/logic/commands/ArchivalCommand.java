package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_INTERNSHIPS;

import seedu.address.model.Model;

/**
 * Lists all internship applications from the archival to the user.
 */
public class ArchivalCommand extends Command {

    public static final String COMMAND_WORD = "archival";

    public static final String MESSAGE_SUCCESS = "Listed all archived internship applications";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredInternshipApplicationList(PREDICATE_SHOW_ARCHIVED_INTERNSHIPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
