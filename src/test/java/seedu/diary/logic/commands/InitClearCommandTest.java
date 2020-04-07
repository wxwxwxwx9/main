package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.Test;

import seedu.diary.logic.parser.ClearCommandConfirmationParser;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;

public class InitClearCommandTest {

    @Test
    public void execute_initClearCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new InitClearCommand(), model, InitClearCommand.MESSAGE_SUCCESS, expectedModel);

        model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

        assertCommandSuccess(new InitClearCommand(), model, InitClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void getNextInternshipDiaryParser_executeSuccess_returnClearCommandConfirmationParser() {
        Command initClearCommand = new InitClearCommand();

        Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

        assertCommandSuccess(initClearCommand, model, InitClearCommand.MESSAGE_SUCCESS, expectedModel);

        assertTrue(initClearCommand.getNextInternshipDiaryParser() instanceof ClearCommandConfirmationParser);
    }

}
