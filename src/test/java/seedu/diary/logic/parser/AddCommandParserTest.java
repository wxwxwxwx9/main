package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.diary.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.diary.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.diary.logic.commands.CommandTestUtil.PRIORITY_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.diary.testutil.TypicalInternshipApplications.BOB;

import org.junit.jupiter.api.Test;

import seedu.diary.logic.commands.AddCommand;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.status.Status;
import seedu.diary.testutil.InternshipApplicationBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        InternshipApplication expectedInternshipApplication = new InternshipApplicationBuilder(BOB).build();

        //whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // multiple companies - last company accepted
        assertParseSuccess(parser, COMPANY_DESC_AMY + COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, COMPANY_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // multiple emails - last email accepted
        assertParseSuccess(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // multiple addresses - last diary accepted
        assertParseSuccess(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // Add new test cases for Role, Priority, Date, Status
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        InternshipApplication expectedInternshipApplication;

        // missing phone
        expectedInternshipApplication = new InternshipApplicationBuilder(BOB).withPhone("").build();
        assertParseSuccess(parser, COMPANY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // missing email
        expectedInternshipApplication = new InternshipApplicationBuilder(BOB).withEmail("").build();
        assertParseSuccess(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // missing diary
        expectedInternshipApplication = new InternshipApplicationBuilder(BOB).withAddress("").build();
        assertParseSuccess(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));

        // missing priority
        expectedInternshipApplication = new InternshipApplicationBuilder(BOB).withPriority("5").build();
        assertParseSuccess(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + DATE_DESC_BOB + STATUS_DESC_BOB,
            new AddCommand(expectedInternshipApplication));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing company prefix
        assertParseFailure(parser, VALID_COMPANY_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, expectedMessage);

        // missing role prefix
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + VALID_ROLE_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + ROLE_DESC_BOB + VALID_DATE_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, expectedMessage);

        // missing status prefix
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + VALID_STATUS_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_COMPANY_BOB + VALID_ROLE_BOB + VALID_DATE_BOB + VALID_STATUS_BOB,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid company
        assertParseFailure(parser, INVALID_COMPANY_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, Company.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, COMPANY_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
            + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // no invalid diary
        // assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
        //         + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, Address.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + INVALID_ROLE_DESC + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB, Role.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + INVALID_DATE_DESC + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            ApplicationDate.MESSAGE_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_BOB + DATE_DESC_BOB + INVALID_PRIORITY_DESC + STATUS_DESC_BOB,
            Priority.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_COMPANY_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + INVALID_ROLE_DESC + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            Company.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + COMPANY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_BOB + DATE_DESC_BOB + PRIORITY_DESC_BOB + STATUS_DESC_BOB,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
