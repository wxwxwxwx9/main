package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        InternshipApplication internshipApplicationToUnarchive =
//                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
//        model.archiveInternshipApplication(internshipApplicationToUnarchive);
//        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
//
//        String expectedMessage =
//                String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_INTERNSHIP_SUCCESS, internshipApplicationToUnarchive);
//
//        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
//        expectedModel.archiveInternshipApplication(internshipApplicationToUnarchive);
//
//        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
//
//        InternshipApplication internshipApplicationToUnarchive =
//                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
//        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
//
//        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_INTERNSHIP_SUCCESS,
//                internshipApplicationToUnarchive);
//
//        Model expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
//        expectedModel.unarchiveInternshipApplication(internshipApplicationToUnarchive);
//        showNoInternshipApplication(expectedModel);
//
//        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternshipDiary().getInternshipList().size());

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_INTERNSHIP_APPLICATION);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different internship application index -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoInternshipApplication(Model model) {
        model.updateFilteredInternshipApplicationList(p -> false);

        assertTrue(model.getFilteredInternshipApplicationList().isEmpty());
    }
}
