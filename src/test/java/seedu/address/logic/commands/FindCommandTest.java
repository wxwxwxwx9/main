package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INTERNSHIP_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void equals() {
        CompanyContainsKeywordsPredicate cFirstPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("first"));
        CompanyContainsKeywordsPredicate cSecondPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("second"));
        RoleContainsKeywordsPredicate rFirstPredicate =
                new RoleContainsKeywordsPredicate(Collections.singletonList("first"));
        RoleContainsKeywordsPredicate rSecondPredicate =
                new RoleContainsKeywordsPredicate(Collections.singletonList("second"));
        AddressContainsKeywordsPredicate aFirstPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate aSecondPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("second"));
        PhoneContainsNumbersPredicate pFirstPredicate =
                new PhoneContainsNumbersPredicate(Collections.singletonList("first"));
        PhoneContainsNumbersPredicate pSecondPredicate =
                new PhoneContainsNumbersPredicate(Collections.singletonList("second"));
        EmailContainsKeywordsPredicate eFirstPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("first"));
        EmailContainsKeywordsPredicate eSecondPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("second"));


        FindCommand findFirstCommand = new FindCommand(cFirstPredicate, rFirstPredicate, aFirstPredicate,
                pFirstPredicate, eFirstPredicate);
        FindCommand findSecondCommand = new FindCommand(cSecondPredicate, rSecondPredicate, aSecondPredicate,
                pSecondPredicate, eSecondPredicate);
        FindCommand findThirdCommand = new FindCommand(cSecondPredicate, rFirstPredicate, aFirstPredicate,
                pFirstPredicate, eFirstPredicate);
        FindCommand findFourthCommand = new FindCommand(cFirstPredicate, rSecondPredicate, aFirstPredicate,
                pFirstPredicate, eFirstPredicate);
        FindCommand findFifthCommand = new FindCommand(cFirstPredicate, rFirstPredicate, aSecondPredicate,
                pFirstPredicate, eFirstPredicate);
        FindCommand findSixthCommand = new FindCommand(cFirstPredicate, rFirstPredicate, aFirstPredicate,
                pSecondPredicate, eFirstPredicate);
        FindCommand findSeventhCommand = new FindCommand(cFirstPredicate, rFirstPredicate, aFirstPredicate,
                pFirstPredicate, eSecondPredicate);


        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(cFirstPredicate, rFirstPredicate, aFirstPredicate,
                pFirstPredicate, eFirstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different argument -> return false
        assertFalse(findFirstCommand.equals(findThirdCommand));
        assertFalse(findFirstCommand.equals(findFourthCommand));
        assertFalse(findFirstCommand.equals(findFifthCommand));
        assertFalse(findFirstCommand.equals(findSixthCommand));
        assertFalse(findFirstCommand.equals(findSeventhCommand));
    }

    @Test
    public void execute_zeroKeywords_noInternshipApplicationFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 0);
        CompanyContainsKeywordsPredicate cPredicate = prepareCompanyPredicate(" ");
        RoleContainsKeywordsPredicate rPredicate = prepareRolePredicate(" ");
        AddressContainsKeywordsPredicate aPredicate = prepareAddressPredicate(" ");
        PhoneContainsNumbersPredicate pPredicate = preparePhonePredicate(" ");
        EmailContainsKeywordsPredicate ePredicate = prepareEmailPredicate(" ");

        FindCommand command = new FindCommand(cPredicate, rPredicate, aPredicate, pPredicate, ePredicate);
        expectedModel.updateFilteredInternshipApplicationList(cPredicate.and(rPredicate)
                .and(aPredicate)
                .and(pPredicate)
                .and(ePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredInternshipApplicationList());
    }

    @Test
    public void execute_multipleKeywords_multipleInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 2);
        CompanyContainsKeywordsPredicate cPredicate = prepareCompanyPredicate("google facebook");
        FindCommand command = new FindCommand(cPredicate, new RoleContainsKeywordsPredicate(null) ,
                new AddressContainsKeywordsPredicate(null), new PhoneContainsNumbersPredicate(null),
                new EmailContainsKeywordsPredicate(null));
        expectedModel.updateFilteredInternshipApplicationList(cPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code CompanyContainsKeywordsPredicate}.
     */
    private CompanyContainsKeywordsPredicate prepareCompanyPredicate(String userInput) {
        return new CompanyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code RoleContainsKeywordsPredicate}.
     */
    private RoleContainsKeywordsPredicate prepareRolePredicate(String userInput) {
        return new RoleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsNumbersPredicate}.
     */
    private PhoneContainsNumbersPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsNumbersPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
