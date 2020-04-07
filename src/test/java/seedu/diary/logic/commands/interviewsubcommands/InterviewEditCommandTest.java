package seedu.diary.logic.commands.interviewsubcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERVIEW;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiaryWithInterviews;
import static seedu.diary.testutil.TypicalInterviews.ONLINE;
import static seedu.diary.testutil.TypicalInterviews.ORCHARD_TOWER;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.testutil.EditInterviewDescriptorBuilder;
import seedu.diary.testutil.InterviewBuilder;

public class InterviewEditCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiaryWithInterviews(), new UserPrefs());
    private InterviewEditCommand.EditInterviewDescriptor editInterviewDescriptor =
        new EditInterviewDescriptorBuilder().build();

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
                null, editInterviewDescriptor));
        assertThrows(NullPointerException.class, () ->
            new InterviewEditCommand(null, INDEX_FIRST_INTERVIEW, editInterviewDescriptor));
        assertThrows(NullPointerException.class, () ->
            new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
                INDEX_FIRST_INTERVIEW, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        InterviewEditCommand command = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW, editInterviewDescriptor);
        assertThrows(NullPointerException.class, () ->
            command.execute(null));
    }

    @Test
    public void execute_validArguments_success() throws CommandException {
        InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        Interview interviewToEdit = internshipApplication.getInterview(INDEX_FIRST_INTERVIEW.getZeroBased());
        InterviewEditCommand command = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW, new EditInterviewDescriptorBuilder(ONLINE).build());
        Interview editedInterview = new InterviewBuilder(ONLINE).build();

        String expectedMessage = String.format(InterviewEditCommand.MESSAGE_EDIT_INTERVIEW_SUCCESS, editedInterview);

        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertTrue(!internshipApplication.hasInterview(interviewToEdit));
    }

    @Test
    public void execute_duplicateInterview_failure() {
        InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        Interview interviewToEdit = internshipApplication.getInterview(INDEX_FIRST_INTERVIEW.getZeroBased());
        InterviewEditCommand command = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW, new EditInterviewDescriptorBuilder(interviewToEdit).build());

        assertThrows(CommandException.class,
            String.format(InterviewEditCommand.MESSAGE_DUPLICATE_INTERVIEW, internshipApplication), () ->
                command.execute(model));
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased()).getInterviews().size() + 1);
        InterviewEditCommand command = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            outOfBoundIndex, editInterviewDescriptor);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX, () ->
            command.execute(model));
    }

    @Test
    public void equals() {
        InterviewEditCommand command = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW, editInterviewDescriptor);
        InterviewEditCommand copy = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW, editInterviewDescriptor);

        //same object -> returns true
        assertTrue(command.equals(command));

        //different object but same variables -> returns true
        assertTrue(command.equals(copy));

        //null -> returns false
        assertFalse(command.equals(null));

        //different type -> returns false
        assertFalse(command.equals(5));

        //different internshipIndex -> returns false
        copy = new InterviewEditCommand(INDEX_SECOND_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW, editInterviewDescriptor);
        assertFalse(command.equals(copy));

        //different interviewIndex -> returns false
        copy = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_SECOND_INTERVIEW, editInterviewDescriptor);
        assertFalse(command.equals(copy));

        //different editInterviewDescriptor -> returns false
        copy = new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            INDEX_FIRST_INTERVIEW, new EditInterviewDescriptorBuilder(ORCHARD_TOWER).build());
        assertFalse(command.equals(copy));
    }
}
