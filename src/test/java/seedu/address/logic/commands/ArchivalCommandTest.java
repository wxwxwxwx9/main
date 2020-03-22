package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ArchivalCommand.
 */
public class ArchivalCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
        expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
    }

    @Test
    public void execute_noArchivedInternshipApplication_showsEmptyList() {
        expectedModel.updateFilteredInternshipApplicationList(Model.PREDICATE_SHOW_ARCHIVED_INTERNSHIPS);
        assertCommandSuccess(new ArchivalCommand(), model, ArchivalCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_archiveOneInternshipApplication_showsOneArchivedInternshipApplication() {
        InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList()
                .get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        model.archiveInternshipApplication(internshipApplication);
        expectedModel.archiveInternshipApplication(internshipApplication);
        expectedModel.updateFilteredInternshipApplicationList(Model.PREDICATE_SHOW_ARCHIVED_INTERNSHIPS);
        assertCommandSuccess(new ArchivalCommand(), model, ArchivalCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
