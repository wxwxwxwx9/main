package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;

/**
 * Selects an internship application to display in the GUI.
 */
public class SelectCommand extends Command {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": displays the details of the internship application selected based on "
        + "the index number in the displayed internship application list.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "\nExample: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SELECT_SUCCESS = "Internship Application Displayed!";

    private Index index;

    public SelectCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<InternshipApplication> currentList = model.getFilteredInternshipApplicationList();

        if (index.getZeroBased() >= currentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        return new CommandResult(MESSAGE_SELECT_SUCCESS, currentList.get(index.getZeroBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectCommand // instanceof handles nulls
            && index.equals(((SelectCommand) other).index)); // state check
    }
}
