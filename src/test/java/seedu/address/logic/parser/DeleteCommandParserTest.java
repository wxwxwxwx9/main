package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SET_FIRST_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.PredicateUtil;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_byIndexValidIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1",
            new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX));
    }

    @Test
    public void parse_byIndexInvalidIndex_throwsParseException() {
        assertParseFailure(parser, "a",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_BY_INDICES));
    }

    @Test
    public void parse_byIndicesValidIndices_returnsDeleteCommand() {
        assertParseSuccess(parser, "1, 2",
            new DeleteCommand(INDEX_SET_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDICES));
    }

    @Test
    public void parse_byIndicesInvalidIndices_throwsParseException() {
        assertParseFailure(parser, "1, a, 3",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_BY_INDICES));
    }

    @Test
    public void parse_byFieldValidField_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete c/google",
            new DeleteCommand(PredicateUtil.prepareCompanyPredicate("google"), CommandExecutionType.BY_FIELD));
    }

    @Test
    public void parse_byFieldInvalidField_throwsParseException() {
        assertParseFailure(parser, "delete d/20 12 2020",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_BY_FIELD));
    }

}
