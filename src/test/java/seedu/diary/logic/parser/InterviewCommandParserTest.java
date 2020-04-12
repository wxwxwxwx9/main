package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.commands.CommandTestUtil.ADDRESS_DESC_NUS;
import static seedu.diary.logic.commands.CommandTestUtil.ADDRESS_DESC_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.DATE_DESC_NUS;
import static seedu.diary.logic.commands.CommandTestUtil.DATE_DESC_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_BOOLEAN_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.ONLINE_DESC_NUS;
import static seedu.diary.logic.commands.CommandTestUtil.ONLINE_DESC_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;
import static seedu.diary.testutil.TypicalInterviews.NUS;
import static seedu.diary.testutil.TypicalInterviews.ONLINE;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.util.BooleanUtil;
import seedu.diary.logic.commands.InterviewCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewAddCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewDeleteCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewEditCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewListCommand;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.testutil.InterviewBuilder;

public class InterviewCommandParserTest {

    private Parser parser = new InterviewCommandParser();

    @Test
    public void parse_generalBadIndex_failure() {
        assertParseFailure(parser, "a list",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_generalBadPreamble_failure() {
        assertParseFailure(parser, "1 list and more",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_generalBadCommandCode_failure() {
        assertParseFailure(parser, "1 notACode",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_listAllFieldsPresent_success() {
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " list",
            new InterviewListCommand(INDEX_FIRST_INTERNSHIP_APPLICATION));
    }

    @Test
    public void parse_addAllFieldsPresent_success() {
        Interview expectedInterview = new InterviewBuilder(NUS).build();
        //with preamble white space
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
                + PREAMBLE_WHITESPACE + ADDRESS_DESC_NUS + ONLINE_DESC_NUS + DATE_DESC_NUS,
            new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, expectedInterview));
        //multiple diary - last diary accepted
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
                + ADDRESS_DESC_ONLINE + ONLINE_DESC_NUS + DATE_DESC_NUS + ADDRESS_DESC_NUS,
            new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, expectedInterview));
        //multiple date - last date accepted
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
                + ADDRESS_DESC_NUS + DATE_DESC_ONLINE + ONLINE_DESC_NUS + DATE_DESC_NUS,
            new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, expectedInterview));
        //multiple isOnline - last isOnline accepted
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
                + ADDRESS_DESC_NUS + ONLINE_DESC_ONLINE + DATE_DESC_NUS + ONLINE_DESC_NUS,
            new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, expectedInterview));
    }

    @Test
    public void parse_addNotOnlineFieldsPresent_success() {
        Interview expectedInterview = new InterviewBuilder(ONLINE).build();
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
                + ADDRESS_DESC_ONLINE + ONLINE_DESC_ONLINE + DATE_DESC_ONLINE,
            new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, expectedInterview));
    }

    @Test
    public void parse_addCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewAddCommand.MESSAGE_USAGE);
        //missing isOnline
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
            + ADDRESS_DESC_NUS + DATE_DESC_NUS, expectedMessage);
        //missing date
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
            + ADDRESS_DESC_NUS + ONLINE_DESC_NUS, expectedMessage);
        //not online but missing address
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            InterviewAddCommand.MESSAGE_OFFLINE_INTERVIEW_ADDRESS);
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
            + ONLINE_DESC_NUS + DATE_DESC_NUS, expectedMessage);
    }

    @Test
    public void parse_addInvalidValue_failure() {
        //invalid diary
        //assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
        //        + INVALID_ADDRESS_DESC + DATE_DESC_NUS + ONLINE_DESC_NUS, Address.MESSAGE_CONSTRAINTS);
        //invalid date
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
            + ADDRESS_DESC_NUS + INVALID_DATE_DESC + ONLINE_DESC_NUS, ApplicationDate.MESSAGE_CONSTRAINTS);
        //invalid boolean
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
            + ADDRESS_DESC_NUS + DATE_DESC_NUS + INVALID_BOOLEAN_DESC, BooleanUtil.INVALID_BOOLEAN);
    }

    @Test
    public void parse_deleteBadPreamble_failure() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " delete ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_deleteValidArgs_success() {
        assertParseSuccess(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " delete "
                + INDEX_FIRST_INTERVIEW.getOneBased(),
            new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, INDEX_FIRST_INTERVIEW));
    }

    @Test
    public void parse_deleteInvalidArgs_failure() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " delete "
            + "notValid", String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_editBadPreamble_failure() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " edit ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewEditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_editInvalidIndex_failure() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " edit "
            + "notValid", String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewEditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_editNoEdits_failure() {
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " edit "
            + INDEX_FIRST_INTERVIEW.getOneBased(), InterviewEditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_editInvalidValue_failure() {
        //invalid date
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " edit "
            + INDEX_FIRST_INTERVIEW.getOneBased() + " "
            + ADDRESS_DESC_NUS + INVALID_DATE_DESC + ONLINE_DESC_NUS, ApplicationDate.MESSAGE_CONSTRAINTS);
        //invalid boolean
        assertParseFailure(parser, INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " edit "
            + INDEX_FIRST_INTERVIEW.getOneBased() + " "
            + ADDRESS_DESC_NUS + DATE_DESC_NUS + INVALID_BOOLEAN_DESC, BooleanUtil.INVALID_BOOLEAN);
    }
}
