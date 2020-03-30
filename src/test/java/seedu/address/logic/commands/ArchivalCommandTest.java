package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ArchivalCommand}.
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
    public void execute_archivalIsNotFiltered_showsSameArchival() {
        model.viewArchivedInternshipApplicationList();
        expectedModel.viewArchivedInternshipApplicationList();
        assertCommandSuccess(new ArchivalCommand(), model, ArchivalCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_archivalIsFiltered_showsEverythingInArchival() {
        model.viewArchivedInternshipApplicationList();
        expectedModel.viewArchivedInternshipApplicationList();
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
        assertCommandSuccess(new ArchivalCommand(), model, ArchivalCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_inMainListView_showsArchivalList() {
        assertCommandSuccess(new ArchivalCommand(), model, ArchivalCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
