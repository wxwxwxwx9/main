package seedu.diary.logic.commands;

import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.logic.parser.InternshipDiaryParser;
import seedu.diary.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Gets the next parser to use, if any.
     *
     * @return a InternshipDiaryParser, or {@code null} if there is none.
     */
    public InternshipDiaryParser getNextInternshipDiaryParser() {
        return null;
    }

}
