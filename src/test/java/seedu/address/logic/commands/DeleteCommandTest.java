package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void execute_byIndexValidIndexUnfilteredList_success() {
        InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);

        String expectedMessage =
            String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipApplicationToDelete);

        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.deleteInternshipApplication(internshipApplicationToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, CommandExecutionType.BY_INDEX);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndexValidIndexFilteredList_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS,
            internshipApplicationToDelete);

        Model expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.deleteInternshipApplication(internshipApplicationToDelete);
        showNoInternshipApplication(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternshipDiary().getDisplayedInternshipList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, CommandExecutionType.BY_INDEX);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommandByIndex =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);
        DeleteCommand deleteSecondCommandByIndex =
                new DeleteCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);

        // same object -> returns true
        assertTrue(deleteFirstCommandByIndex.equals(deleteFirstCommandByIndex));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);
        assertTrue(deleteFirstCommandByIndex.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandByIndex.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandByIndex.equals(null));

        // different internship application index -> returns false
        assertFalse(deleteFirstCommandByIndex.equals(deleteSecondCommandByIndex));
    }

    /**
     * Updates {@code model}'s filtered list to show no applications.
     */
    private void showNoInternshipApplication(Model model) {
        model.updateFilteredInternshipApplicationList(p -> false);

        assertTrue(model.getFilteredInternshipApplicationList().isEmpty());
    }
}
