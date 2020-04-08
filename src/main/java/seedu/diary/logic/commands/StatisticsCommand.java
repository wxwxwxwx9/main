package seedu.diary.logic.commands;

import seedu.diary.model.Model;

/**
 * Displays statistics window UI to users.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows internship application statistics.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_STATISTICS_MESSAGE = "Opened statistics window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_STATISTICS_MESSAGE, false, true, false);
    }
}
