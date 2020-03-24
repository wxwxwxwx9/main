package seedu.address.logic.commands.interviewsubcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.interviewsubcommands.InterviewListCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.testutil.TypicalInternshipApplications;


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
                String.format(MESSAGE_SUCCESS, internshipApplication), expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        assertCommandFailure(new InterviewListCommand(Index.fromZeroBased(10000)),
                model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }
}
