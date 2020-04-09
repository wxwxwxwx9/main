package seedu.diary.logic.commands;

import static seedu.diary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    }

    @Test
    public void execute_newInternshipApplication_success() {
        InternshipApplication validInternshipApplication = new InternshipApplicationBuilder().build();

        Model expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.addInternshipApplication(validInternshipApplication);

        assertCommandSuccess(new AddCommand(validInternshipApplication), model,
            String.format(AddCommand.MESSAGE_SUCCESS, validInternshipApplication), expectedModel,
            validInternshipApplication);
    }

    @Test
    public void execute_duplicateInternshipApplication_throwsCommandException() {
        InternshipApplication internshipInList = model.getInternshipDiary().getDisplayedInternshipList().get(0);
        assertCommandFailure(new AddCommand(internshipInList), model, AddCommand.MESSAGE_DUPLICATE_INTERNSHIP);
    }

}
