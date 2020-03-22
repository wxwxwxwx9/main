package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewDeleteCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewEditCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewListCommand;

public class InterviewCommandParserTest {

    private Parser parser = new InterviewCommandParser();

    @Test
    public void parse_general_badIndex() {
        assertParseFailure(parser, "a list",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_general_badPreamble() {
        assertParseFailure(parser, "1 list and more",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_general_badCommandCode() {
        assertParseFailure(parser, "1 notACode",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_list_allFieldsPresent() {
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " list",
                new InterviewListCommand(INDEX_FIRST_INTERNSHIP_APPLICATION));
    }

    @Test
    public void parse_delete_badPreamble() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " delete ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_delete_validArgs() {
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " delete "
                + INDEX_FIRST_INTERVIEW.getOneBased(),
                new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, INDEX_FIRST_INTERVIEW));
    }

    @Test
    public void parse_delete_invalidArgs() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " delete "
                + "notValid", String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_edit_badPreamble() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " edit ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewEditCommand.MESSAGE_USAGE));
    }

}
