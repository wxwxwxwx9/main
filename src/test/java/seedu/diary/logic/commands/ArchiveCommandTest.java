package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Contains integration tests (interaction with the Model, and Archival Command) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    // default view is unarchived internship list (no need to set view)
    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void execute_archiveInternshipApplication_success() {

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        try {
            archiveCommand.execute(expectedModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        InternshipApplication archivedInternshipApplication =
            expectedModel.getAllInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        assertTrue(archivedInternshipApplication.isArchived());
    }

    @Test
    public void execute_archiveAlreadyArchivedInternshipApplication_throwsException() {

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        try {
            archiveCommand.execute(expectedModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        expectedModel.viewArchivedInternshipApplicationList();

        assertThrows(CommandException.class, () -> archiveCommand.execute(expectedModel));
    }

    @Test
    public void execute_archiveOneInternshipApplication_archivalViewCorrect() {

        // model
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        try {
            archiveCommand.execute(model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
        model.viewArchivedInternshipApplicationList();

        // expected model
        InternshipApplication ia = expectedModel.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        expectedModel.archiveInternshipApplication(ia);
        expectedModel.viewArchivedInternshipApplicationList();

        // can't use assertCommandSuccess because have to change view of internshipDiary after execution of command
        // assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
        assertEquals(model, expectedModel);
    }

    @Test
    public void execute_archiveOneInternshipApplication_listViewCorrect() {

        // model
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);

        // expected model
        InternshipApplication ia = expectedModel.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        expectedModel.archiveInternshipApplication(ia);

        String expectedMessage = ia.toString();

        // default view is unarchived so we can use assertCommandSuccess
        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
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

}
