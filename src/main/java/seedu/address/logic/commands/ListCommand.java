package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all unarchived internship application(s) from the internship diary to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all internship applications";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.viewUnarchivedInternshipApplicationList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
