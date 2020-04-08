package seedu.diary.logic.commands;

import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(
            SHOWING_HELP_MESSAGE, true, false, false
        );
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
