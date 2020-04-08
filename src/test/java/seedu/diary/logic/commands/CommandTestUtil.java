package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_IS_ONLINE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.diary.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.logic.commands.interviewsubcommands.InterviewEditCommand;
import seedu.diary.model.InternshipDiary;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.interview.OnlineInterview;
import seedu.diary.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.diary.testutil.EditInternshipDescriptorBuilder;
import seedu.diary.testutil.EditInterviewDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_COMPANY_AMY = "Amy";
    public static final String VALID_COMPANY_BOB = "Bob";
    public static final String VALID_ROLE_AMY = "Software Engineer";
    public static final String VALID_ROLE_BOB = "Designer";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DATE_AMY = "03 04 2016";
    public static final String VALID_DATE_BOB = "01 02 2020";
    public static final String VALID_PRIORITY_AMY = "2";
    public static final String VALID_PRIORITY_BOB = "1";
    public static final String VALID_STATUS_AMY = "WISHLIST";
    public static final String VALID_STATUS_BOB = "APPLIED";
    public static final String VALID_LAST_STAGE_AMY = "WISHLIST";
    public static final String VALID_LAST_STAGE_BOB = "APPLIED";

    public static final String VALID_ADDRESS_NUS = "123 Kent Ridge Road";
    public static final String VALID_ADDRESS_ONLINE = OnlineInterview.ADDRESS_NOT_APPLICABLE.toString();
    public static final String VALID_DATE_NUS = "10 10 2010";
    public static final String VALID_DATE_ONLINE = "20 02 2020";
    public static final String VALID_IS_ONLINE_NUS = "false";
    public static final String VALID_IS_ONLINE_ONLINE = "true";

    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String PRIORITY_DESC_AMY = " " + PREFIX_PRIORITY + VALID_PRIORITY_AMY;
    public static final String PRIORITY_DESC_BOB = " " + PREFIX_PRIORITY + VALID_PRIORITY_BOB;
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;

    public static final String ADDRESS_DESC_NUS = " " + PREFIX_ADDRESS + VALID_ADDRESS_NUS;
    public static final String ADDRESS_DESC_ONLINE = " " + PREFIX_ADDRESS + VALID_ADDRESS_ONLINE;
    public static final String DATE_DESC_NUS = " " + PREFIX_DATE + VALID_DATE_NUS;
    public static final String DATE_DESC_ONLINE = " " + PREFIX_DATE + VALID_DATE_ONLINE;
    public static final String ONLINE_DESC_NUS = " " + PREFIX_IS_ONLINE + VALID_IS_ONLINE_NUS;
    public static final String ONLINE_DESC_ONLINE = " " + PREFIX_IS_ONLINE + VALID_IS_ONLINE_ONLINE;

    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + "Google&"; // '&' not allowed in company
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "So&"; // '&' not allowed in company
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS + " "; // no invalid addresses
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "010420"; // date format is DD MM YYYY
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "a"; // only numbers allowed in priority
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "Not"; // 'Not' is not a status
    public static final String INVALID_BOOLEAN_DESC = " " + PREFIX_IS_ONLINE + "n"; // 'n' is not a boolean

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditInternshipDescriptor DESC_AMY;
    public static final EditCommand.EditInternshipDescriptor DESC_BOB;
    public static final InterviewEditCommand.EditInterviewDescriptor DESC_NUS;
    public static final InterviewEditCommand.EditInterviewDescriptor DESC_ONLINE;

    static {
        DESC_AMY = new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_AMY)
            .withRole(VALID_ROLE_AMY).withApplicationDate(VALID_DATE_AMY).withPriority(VALID_PRIORITY_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withStatus(VALID_STATUS_AMY).build();
        DESC_BOB = new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
            .withRole(VALID_ROLE_BOB).withApplicationDate(VALID_DATE_BOB).withPriority(VALID_PRIORITY_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withStatus(VALID_STATUS_BOB).build();
        DESC_NUS = new EditInterviewDescriptorBuilder().withAddress(VALID_ADDRESS_NUS)
            .withInterviewDate(VALID_DATE_NUS).withIsOnline(VALID_IS_ONLINE_NUS).build();
        DESC_ONLINE = new EditInterviewDescriptorBuilder().withAddress(VALID_ADDRESS_ONLINE)
            .withInterviewDate(VALID_DATE_ONLINE).withIsOnline(VALID_IS_ONLINE_ONLINE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
        Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
        Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Compares to a command that takes in an internship application to display.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
        Model expectedModel, InternshipApplication internshipApplication) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the internship diary, filtered internship application list and selected internship application in
     * {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        InternshipDiary expectedInternshipDiary = new InternshipDiary(actualModel.getInternshipDiary());
        List<InternshipApplication> expectedFilteredList =
            new ArrayList<>(actualModel.getFilteredInternshipApplicationList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedInternshipDiary, actualModel.getInternshipDiary());
        assertEquals(expectedFilteredList, actualModel.getFilteredInternshipApplicationList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the internship application at the given
     * {@code targetIndex} in the
     * {@code model}'s internship diary.
     */
    public static void showInternshipApplicationAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredInternshipApplicationList().size());

        InternshipApplication internshipApplication =
            model.getFilteredInternshipApplicationList().get(targetIndex.getZeroBased());
        final String[] splitName = internshipApplication.getCompany().fullCompany.split("\\s+");
        model.updateFilteredInternshipApplicationList(
            new CompanyContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredInternshipApplicationList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the internship application at the given
     * {@code targetIndices} in the
     * {@code model}'s internship diary.
     */
    public static void showInternshipApplicationAtIndices(Model model, List<Index> targetIndices) {
        // check that all indices are valid
        for (Index targetIndex : targetIndices) {
            assertTrue(targetIndex.getZeroBased() < model.getFilteredInternshipApplicationList().size());
        }

        List<InternshipApplication> internshipApplications = new ArrayList<>();

        // get all internship applications and place them in list
        for (Index targetIndex : targetIndices) {
            InternshipApplication internshipApplication =
                model.getFilteredInternshipApplicationList().get(targetIndex.getZeroBased());
            internshipApplications.add(internshipApplication);
        }

        List<String> keywords = new ArrayList<>();

        // extract all company name keywords from internship applications
        for (InternshipApplication internshipApplication : internshipApplications) {
            final String[] splitName = internshipApplication.getCompany().fullCompany.split("\\s+");
            for (String name : splitName) {
                keywords.add(name);
            }
        }

        // filter internship applications list based on the company name keywords
        model.updateFilteredInternshipApplicationList(new CompanyContainsKeywordsPredicate(keywords));

    }

}
