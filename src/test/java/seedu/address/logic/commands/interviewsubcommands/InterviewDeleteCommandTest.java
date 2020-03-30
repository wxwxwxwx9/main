package seedu.address.logic.commands.interviewsubcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERVIEW;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiaryWithInterviews;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

/**
 * Contains integration test with model and unit tests for {@code InterviewDeleteCommand}.
 */
public class InterviewDeleteCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiaryWithInterviews(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, null));
        assertThrows(NullPointerException.class, () ->
            new InterviewDeleteCommand(null, INDEX_FIRST_INTERVIEW));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        InterviewDeleteCommand command = new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW);
        assertThrows(NullPointerException.class, () ->
            command.execute(null));
    }

    @Test
    public void execute_validIndex_success() throws CommandException {
        InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        Interview interviewToDelete = internshipApplication.getInterview(INDEX_FIRST_INTERVIEW.getZeroBased());
        InterviewDeleteCommand command = new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW);
        String expectedMessage = String.format(InterviewDeleteCommand.MESSAGE_SUCCESS, interviewToDelete);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertTrue(!internshipApplication.hasInterview(interviewToDelete));
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased()).getInterviews().size() + 1);
        InterviewDeleteCommand command = new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            outOfBoundIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX, () ->
            command.execute(model));
    }

    @Test
    public void equals() {
        InterviewDeleteCommand command = new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW);
        InterviewDeleteCommand copy = new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW);

        //same object -> returns true
        assertTrue(command.equals(command));

        //different object but same variables -> returns true
        assertTrue(command.equals(copy));

        //null -> returns false
        assertFalse(command.equals(null));

        //different type -> returns false
        assertFalse(command.equals(5));

        //different internshipIndex -> returns false
        copy = new InterviewDeleteCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, INDEX_FIRST_INTERVIEW);
        assertFalse(command.equals(copy));

        //different interviewIndex -> returns false
        copy = new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, INDEX_SECOND_INTERVIEW);
        assertFalse(command.equals(copy));
    }
}
