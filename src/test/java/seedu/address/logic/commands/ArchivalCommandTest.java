package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.testutil.InternshipApplicationBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ArchivalCommand}.
 */
public class ArchivalCommandTest {

    private Model model;
    private Model expectedModel;

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
        expectedModel.viewArchivedInternshipApplicationList();
        assertCommandSuccess(new ArchivalCommand(), model, ArchivalCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
