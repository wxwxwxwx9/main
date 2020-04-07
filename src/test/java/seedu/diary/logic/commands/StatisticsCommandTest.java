package seedu.diary.logic.commands;

import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.logic.commands.StatisticsCommand.SHOWING_STATISTICS_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;

/**
 * Contains unit tests for {@code StatisticsCommand}.
 */
public class StatisticsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_statistics_success() {
        CommandResult expectedCommandResult = new CommandResult(
            SHOWING_STATISTICS_MESSAGE, false, true, false
        );
        assertCommandSuccess(new StatisticsCommand(), model, expectedCommandResult, expectedModel);
    }
}
