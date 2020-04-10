package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_LIST_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.commandexecutiontype.RemovalBasedCommandExecutionType;
import seedu.diary.logic.commands.DeleteCommand;
import seedu.diary.logic.commands.RemovalBasedCommand;
import seedu.diary.model.status.Status;
import seedu.diary.testutil.PredicateUtil;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemovalBasedCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemovalBasedCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 *
 * We will only be testing integration with DeleteCommand. Archive and Unarchive command are similar
 * to delete command in nature and it is unnecessary to test those commands.
 */
public class RemovalBasedCommandExecutionTypeParserTest {

    private final String validFieldStatus = PREFIX_STATUS.toString();
    private final String invalidFieldDate = PREFIX_DATE.toString();

    // Currently only this class requires such test inputs, should there be more in the future, we will
    // create a TestUtil class that can contain all such typical test inputs
    private final String sampleDate = "20 12 2020";
    private final String invalidIndex = " a";
    private final String invalidIndices = "1, a, 3";

    private final String commandWord = DeleteCommand.COMMAND_WORD;

    private final RemovalBasedCommandExecutionTypeParser parser =
        new RemovalBasedCommandExecutionTypeParser(commandWord);

    @Test
    public void parse_byIndexValidIndex_returnsRemovalBasedCommand() {
        String validIndexInput = Integer.toString(INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased());
        assertParseSuccess(parser, validIndexInput,
            new RemovalBasedCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, RemovalBasedCommandExecutionType.BY_INDEX,
                commandWord)
        );
    }

    @Test
    public void parse_byIndexInvalidIndex_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemovalBasedCommand.MESSAGE_USAGE_BY_INDEX.apply(commandWord));
        assertParseFailure(parser, invalidIndex, expectedMessage);
    }

    @Test
    public void parse_byIndicesValidIndices_returnsRemovalBasedCommand() {
        String index1 = Integer.toString(INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased());
        String index2 = Integer.toString(INDEX_SECOND_INTERNSHIP_APPLICATION.getOneBased());
        String validIndicesInput = index1 + ", " + index2;
        assertParseSuccess(parser, validIndicesInput,
            new RemovalBasedCommand(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION,
                RemovalBasedCommandExecutionType.BY_INDICES,
                commandWord)
        );
    }

    @Test
    public void parse_byIndicesInvalidIndices_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemovalBasedCommand.MESSAGE_USAGE_BY_INDICES.apply(commandWord));
        assertParseFailure(parser, invalidIndices, expectedMessage);
    }

    @Test
    public void parse_byFieldValidField_returnsRemovalBasedCommand() {
        String validCommand = String.format("%s %s%s", commandWord, validFieldStatus, Status.APPLIED);
        assertParseSuccess(parser, validCommand,
            new RemovalBasedCommand(PredicateUtil.prepareStatusPredicate(Status.APPLIED.toString()),
                RemovalBasedCommandExecutionType.BY_FIELD,
                commandWord)
        );
    }

    @Test
    public void parse_byFieldInvalidField_throwsParseException() {
        String invalidCommand = String.format("%s %s%s", commandWord, invalidFieldDate, sampleDate);
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemovalBasedCommand.MESSAGE_USAGE_BY_FIELD.apply(commandWord));
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }

}
