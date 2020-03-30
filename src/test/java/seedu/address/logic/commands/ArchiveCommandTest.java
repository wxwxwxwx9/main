package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model, and Archival Command) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model;
    private ModelManager expectedModel;

    @BeforeEach
    public void setUp() {
        // create and load internship diaries
        InternshipDiary firstInternshipDiary = new InternshipDiary();
        firstInternshipDiary.loadInternshipApplication(new InternshipApplicationBuilder().build());
        InternshipDiary secondInternshipDiary = new InternshipDiary();
        secondInternshipDiary.loadInternshipApplication(new InternshipApplicationBuilder().build());

        // create models
        model = new ModelManager(firstInternshipDiary, new UserPrefs());
        expectedModel = new ModelManager(secondInternshipDiary, new UserPrefs());

        // default view is unarchived internship list (no need to set view)
    }

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

        String expectedMessage =
                String.format(ArchiveCommand.MESSAGE_ARCHIVE_INTERNSHIP_SUCCESS, ia);

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
