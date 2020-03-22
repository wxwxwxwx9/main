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
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        InternshipApplication internshipApplicationToArchive =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);

        String expectedMessage =
                String.format(ArchiveCommand.MESSAGE_ARCHIVE_INTERNSHIP_SUCCESS, internshipApplicationToArchive);

        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.archiveInternshipApplication(internshipApplicationToArchive);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    /*
    @Test
    public void execute_validIndexFilteredList_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        InternshipApplication internshipApplicationToArchive =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_INTERNSHIP_SUCCESS,
                internshipApplicationToArchive);

        Model expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.archiveInternshipApplication(internshipApplicationToArchive);
        showNoInternshipApplication(expectedModel);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternshipDiary().getInternshipList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_INTERNSHIP_APPLICATION);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different internship application index -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoInternshipApplication(Model model) {
        model.updateFilteredInternshipApplicationList(p -> false);

        assertTrue(model.getFilteredInternshipApplicationList().isEmpty());
    }
}
