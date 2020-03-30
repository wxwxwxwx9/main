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

/**
 * Primarily unit tests for {@code SelectCommand}, as command does not modify model.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        SelectCommand selectCommand = new SelectCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);

        assertCommandSuccess(selectCommand, model, SelectCommand.MESSAGE_SELECT_SUCCESS, model,
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased()));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        SelectCommand selectCommand = new SelectCommand(outOfBoundIndex);
        assertCommandFailure(selectCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
        SelectCommand selectCommand = new SelectCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        assertCommandSuccess(selectCommand, model, SelectCommand.MESSAGE_SELECT_SUCCESS, model,
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased()));
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
        SelectCommand selectCommand = new SelectCommand(INDEX_SECOND_INTERNSHIP_APPLICATION);
        assertCommandFailure(selectCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_INTERNSHIP_APPLICATION);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different internship application index -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }
}
