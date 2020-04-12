package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.diary.model.Model;

/**
 * Lists all archived internship application(s) from the internship diary to the user.
 */
public class ArchivalCommand extends Command {

    public static final String COMMAND_WORD = "archival";

    public static final String MESSAGE_SUCCESS = "Listed all archived internship application(s)";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.viewArchivedInternshipApplicationList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
