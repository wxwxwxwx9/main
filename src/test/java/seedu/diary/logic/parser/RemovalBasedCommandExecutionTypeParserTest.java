package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import seedu.diary.testutil.RemovalBasedCommandExecutionTypeUtil;

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

    private static final String company = "google";
    private static final String date = "20 12 2020";

    private static final String VALID_FIELD_STATUS = "s/";
    private static final String INVALID_FIELD_DATE = "d/";

    private static final String commandWord = DeleteCommand.COMMAND_WORD;

    private RemovalBasedCommandExecutionTypeParser parser =
        new RemovalBasedCommandExecutionTypeParser(DeleteCommand.COMMAND_WORD);

    @Test
    public void parse_byIndexValidIndex_returnsRemovalBasedCommand() {
        String validIndexInput = RemovalBasedCommandExecutionTypeUtil.VALID_INDEX;
        assertParseSuccess(parser, validIndexInput,
            new RemovalBasedCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, RemovalBasedCommandExecutionType.BY_INDEX,
                DeleteCommand.COMMAND_WORD)
        );
    }

    @Test
    public void parse_byIndexInvalidIndex_throwsParseException() {
        String invalidIndexInput = RemovalBasedCommandExecutionTypeUtil.INVALID_INDEX;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemovalBasedCommand.MESSAGE_USAGE_BY_INDEX.apply(commandWord));
        assertParseFailure(parser, invalidIndexInput, expectedMessage);
    }

    @Test
    public void parse_byIndicesValidIndices_returnsRemovalBasedCommand() {
        String index1 = INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + "";
        String index2 = INDEX_SECOND_INTERNSHIP_APPLICATION.getOneBased() + "";
        String validIndicesInput = index1 + ", " + index2;
        assertParseSuccess(parser, validIndicesInput,
            new RemovalBasedCommand(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION,
                RemovalBasedCommandExecutionType.BY_INDICES,
                commandWord)
        );
    }

    @Test
    public void parse_byIndicesInvalidIndices_throwsParseException() {
        String invalidIndicesInput = RemovalBasedCommandExecutionTypeUtil.INVALID_INDICES;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemovalBasedCommand.MESSAGE_USAGE_BY_INDICES.apply(commandWord));
        assertParseFailure(parser, invalidIndicesInput, expectedMessage);
    }

    @Test
    public void parse_byFieldValidField_returnsRemovalBasedCommand() {
        String validCommand = String.format("%s %s%s", commandWord, VALID_FIELD_STATUS, Status.APPLIED);
        assertParseSuccess(parser, validCommand,
            new RemovalBasedCommand(PredicateUtil.prepareStatusPredicate(Status.APPLIED.toString()),
                RemovalBasedCommandExecutionType.BY_FIELD,
                commandWord)
        );
    }

    @Test
    public void parse_byFieldInvalidField_throwsParseException() {
        String invalidCommand = String.format("%s %s%s", commandWord, INVALID_FIELD_DATE, date);
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemovalBasedCommand.MESSAGE_USAGE_BY_FIELD.apply(commandWord));
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }

}
