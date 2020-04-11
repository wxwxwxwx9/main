package seedu.diary.logic.parser;

import seedu.diary.logic.commands.ClearCommand;
import seedu.diary.logic.commands.Command;
import seedu.diary.logic.parser.exceptions.ClearCommandConfirmationParseException;
import seedu.diary.logic.parser.exceptions.ParseException;

/**
 * Parses user input to confirm clear command.
 */
public class ClearCommandConfirmationParser extends InternshipDiaryParser {
    public static final String CONFIRMATION_COMMAND_WORD = "yes";
    public static final String CANCEL_CLEAR_COMMAND = "Internship diary is not cleared. No data will be lost.";

    @Override
    public Command parseCommand(String userInput) throws ParseException {
        if (userInput.trim().equalsIgnoreCase(CONFIRMATION_COMMAND_WORD)) {
            return new ClearCommand();
        } else {
            throw new ClearCommandConfirmationParseException(CANCEL_CLEAR_COMMAND);
        }
    }
}
