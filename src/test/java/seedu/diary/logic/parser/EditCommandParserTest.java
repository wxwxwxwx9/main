package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.DATE_DESC_AMY;
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
import static seedu.diary.logic.commands.CommandTestUtil.PRIORITY_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_THIRD_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.EditCommand;
import seedu.diary.logic.commands.EditCommand.EditInternshipDescriptor;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.status.Status;
import seedu.diary.testutil.EditInternshipDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_COMPANY_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + COMPANY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + COMPANY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC, Company.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC,
            Priority.MESSAGE_CONSTRAINTS); // invalid priority
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS); // invalid status
        assertParseFailure(parser, "1" + INVALID_DATE_DESC,
            ApplicationDate.MESSAGE_CONSTRAINTS); // invalid date

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC + INVALID_EMAIL_DESC
                + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
            Company.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + ROLE_DESC_AMY + PRIORITY_DESC_AMY
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + COMPANY_DESC_AMY + DATE_DESC_AMY + STATUS_DESC_BOB;

        EditInternshipDescriptor descriptor = new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_AMY)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withRole(VALID_ROLE_AMY).withPriority(VALID_PRIORITY_AMY).withStatus(VALID_STATUS_BOB)
            .withApplicationDate(VALID_DATE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_INTERNSHIP_APPLICATION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditCommand.EditInternshipDescriptor descriptor =
            new EditInternshipDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // company
        Index targetIndex = INDEX_THIRD_INTERNSHIP_APPLICATION;
        String userInput = targetIndex.getOneBased() + COMPANY_DESC_AMY;
        EditInternshipDescriptor descriptor =
            new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditInternshipDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditInternshipDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // diary
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditInternshipDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //Add new test cases for Role, Priority, Date, Status
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_INTERNSHIP_APPLICATION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
            + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
            + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB;

        EditCommand.EditInternshipDescriptor descriptor =
            new EditInternshipDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_INTERNSHIP_APPLICATION;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditInternshipDescriptor descriptor = new EditInternshipDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
            + PHONE_DESC_BOB;
        descriptor = new EditInternshipDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
