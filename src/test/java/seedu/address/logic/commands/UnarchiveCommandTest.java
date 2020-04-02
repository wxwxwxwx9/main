package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model and Archival Command) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model model;
    private ModelManager expectedModel;

    @BeforeEach
    public void setUp() {
        // create archived internship applications
        InternshipApplication firstInternshipApplicationArchived = new InternshipApplicationBuilder().build();
        firstInternshipApplicationArchived = firstInternshipApplicationArchived.archive();
        InternshipApplication secondInternshipApplicationArchived = new InternshipApplicationBuilder().build();
        secondInternshipApplicationArchived = secondInternshipApplicationArchived.archive();

        // create and load internship diaries
        InternshipDiary firstInternshipDiary = new InternshipDiary();
        firstInternshipDiary.loadInternshipApplication(firstInternshipApplicationArchived);

        InternshipDiary secondInternshipDiary = new InternshipDiary();
        secondInternshipDiary.loadInternshipApplication(secondInternshipApplicationArchived);

        // create models
        model = new ModelManager(firstInternshipDiary, new UserPrefs());
        expectedModel = new ModelManager(secondInternshipDiary, new UserPrefs());

        // view is archived internship list
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

        assertTrue(!archivedInternshipApplication.isArchived());
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
        ;
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
