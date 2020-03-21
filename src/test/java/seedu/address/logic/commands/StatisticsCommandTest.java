package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.StatisticsCommand.SHOWING_STATISTICS_MESSAGE;

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
