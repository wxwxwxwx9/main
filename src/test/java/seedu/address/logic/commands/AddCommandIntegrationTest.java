package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.testutil.InternshipApplicationBuilder;

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
    public void execute_newPerson_success() {
        InternshipApplication validInternshipApplication = new InternshipApplicationBuilder().build();

        Model expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.addInternshipApplication(validInternshipApplication);

        assertCommandSuccess(new AddCommand(validInternshipApplication), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validInternshipApplication), expectedModel,
                validInternshipApplication);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        InternshipApplication internshipInList = model.getInternshipDiary().getDisplayedInternshipList().get(0);
        assertCommandFailure(new AddCommand(internshipInList), model, AddCommand.MESSAGE_DUPLICATE_INTERNSHIP);
    }

}
