package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.InternshipDiary;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Contains integration tests (interaction with the Model and Archival Command) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        // create mock internship diary and archive first internship application
        InternshipDiary mockInternshipDiary = getTypicalInternshipDiary();

        InternshipApplication iaToArchive = mockInternshipDiary
            .getDisplayedInternshipList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        mockInternshipDiary.archiveInternshipApplication(iaToArchive);

        // create models
        model = new ModelManager(mockInternshipDiary, new UserPrefs());
        expectedModel = new ModelManager(mockInternshipDiary, new UserPrefs());

        // switch view to archived internship list
        model.viewArchivedInternshipApplicationList();
        expectedModel.viewArchivedInternshipApplicationList();
    }

    @Test
    public void execute_unarchiveInternshipApplication_success() {

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        try {
            unarchiveCommand.execute(expectedModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        InternshipApplication archivedInternshipApplication =
            expectedModel.getAllInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        assertFalse(archivedInternshipApplication.isArchived());
    }

    @Test
    public void execute_unarchiveAlreadyUnarchivedInternshipApplication_throwsException() {

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        try {
            unarchiveCommand.execute(expectedModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        expectedModel.viewUnarchivedInternshipApplicationList();

        assertThrows(CommandException.class, () -> unarchiveCommand.execute(expectedModel));
    }


    @Test
    public void execute_unarchiveOneInternshipApplication_listViewCorrect() {

        // model
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        try {
            unarchiveCommand.execute(model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
        model.viewUnarchivedInternshipApplicationList();

        // expected model
        InternshipApplication ia = expectedModel.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        expectedModel.unarchiveInternshipApplication(ia);
        expectedModel.viewUnarchivedInternshipApplicationList();

        // can't use assertCommandSuccess because have to change view of internshipDiary after execution of command
        // assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
        assertEquals(model, expectedModel);
    }

    @Test
    public void execute_unarchiveOneInternshipApplication_archivalViewSuccess() {

        // model
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);

        // expected model
        InternshipApplication ia = expectedModel.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        expectedModel.unarchiveInternshipApplication(ia);

        String expectedMessage = ia.toString();

        // default view is unarchived so we can use assertCommandSuccess
        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
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

}
