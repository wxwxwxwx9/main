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
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void equals() {
        CompanyContainsKeywordsPredicate firstPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("first"));
        CompanyContainsKeywordsPredicate secondPredicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noInternshipApplicationFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 0);
        CompanyContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredInternshipApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredInternshipApplicationList());
    }

    /*
    @Test
    public void execute_multipleKeywords_multipleInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 3);
        CompanyContainsKeywordsPredicate predicate = preparePredicate("amy bob");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredInternshipApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AMY, BOB), model.getFilteredInternshipApplicationList());
    }*/

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private CompanyContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CompanyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
