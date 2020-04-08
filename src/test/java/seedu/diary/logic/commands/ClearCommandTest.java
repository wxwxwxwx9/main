package seedu.diary.logic.commands;

import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.Test;

import seedu.diary.model.InternshipDiary;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyInternshipDiary_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyInternshipDiary_success() {
        Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
        expectedModel.setInternshipDiary(new InternshipDiary());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
