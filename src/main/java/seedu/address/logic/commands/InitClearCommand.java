package seedu.address.logic.commands;

import seedu.address.logic.parser.ClearCommandConfirmationParser;
import seedu.address.logic.parser.InternshipDiaryParser;
import seedu.address.model.Model;

/**
 * Clears the internship diary.
 */
public class InitClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Do you really want to delete all data in Internship Diary?\n"
            + "Type 'yes' to confirm.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public InternshipDiaryParser getNextInternshipDiaryParser() {
        return new ClearCommandConfirmationParser();
    }
}
