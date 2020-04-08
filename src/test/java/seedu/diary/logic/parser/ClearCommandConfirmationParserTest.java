package seedu.diary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.diary.logic.commands.ClearCommand;
import seedu.diary.logic.parser.exceptions.ParseException;

public class ClearCommandConfirmationParserTest {

    private final InternshipDiaryParser parser = new ClearCommandConfirmationParser();

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(
            ClearCommandConfirmationParser.CONFIRMATION_COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(
            ClearCommandConfirmationParser.CONFIRMATION_COMMAND_WORD + " ") instanceof ClearCommand);
        assertTrue(parser.parseCommand(
            ClearCommandConfirmationParser.CONFIRMATION_COMMAND_WORD.toUpperCase()) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, ClearCommandConfirmationParser.CANCEL_CLEAR_COMMAND, () ->
            parser.parseCommand(ClearCommandConfirmationParser.CONFIRMATION_COMMAND_WORD + " 3"));
        assertThrows(ParseException.class, ClearCommandConfirmationParser.CANCEL_CLEAR_COMMAND, () ->
            parser.parseCommand(" "));
    }
}
