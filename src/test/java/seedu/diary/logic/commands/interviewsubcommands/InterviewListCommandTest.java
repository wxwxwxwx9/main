package seedu.diary.logic.commands.interviewsubcommands;

import static seedu.diary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.logic.commands.interviewsubcommands.InterviewListCommand.MESSAGE_SUCCESS;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.testutil.TypicalInternshipApplications;


public class InterviewListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalInternshipApplications
            .getTypicalInternshipDiaryWithInterviews(), new UserPrefs());
        expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new InterviewListCommand(null));
    }

    @Test
    public void execute_validIndex_success() {
        InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList()
            .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        assertCommandSuccess(new InterviewListCommand(INDEX_FIRST_INTERNSHIP_APPLICATION), model,
            String.format(MESSAGE_SUCCESS, internshipApplication), expectedModel,
            internshipApplication);
    }

    @Test
    public void execute_invalidIndex_failure() {
        assertCommandFailure(new InterviewListCommand(Index.fromZeroBased(10000)),
            model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }
}
