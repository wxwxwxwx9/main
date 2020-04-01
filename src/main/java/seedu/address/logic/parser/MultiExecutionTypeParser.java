package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface MultiExecutionTypeParser<MultiExecutionTypeCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    MultiExecutionTypeCommand parse(String userInput) throws ParseException;
}
