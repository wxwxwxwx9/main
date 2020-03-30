package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ReminderCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.comparator.ApplicationDateAndInterviewDateComparator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.predicate.ApplicationDateDuePredicate;

class ReminderCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        model = new ModelManager(getTypicalInternshipDiary(), userPrefs);
        expectedModel = new ModelManager(model.getInternshipDiary(), userPrefs);
    }

    @Test
    public void execute_afterReminder_showsFilteredList() {
        expectedModel.updateFilteredInternshipApplicationList(new ApplicationDateDuePredicate());
        expectedModel.updateFilteredInternshipApplicationList(new ApplicationDateAndInterviewDateComparator());
        CommandResult expectedMessage = new CommandResult(MESSAGE_SUCCESS);
        assertEquals(expectedMessage, new ReminderCommand().execute(model));
        assertCommandSuccess(new ReminderCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ReminderCommand firstReminder = new ReminderCommand();

        // same object -> returns true
        assertEquals(firstReminder, firstReminder);

        // different types -> returns false
        assertNotEquals("reminder", firstReminder);

        // null -> returns false
        assertNotEquals(null, firstReminder);
    }
}
