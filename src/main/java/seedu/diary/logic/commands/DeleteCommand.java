package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Deletes an internship application identified using its displayed index from the internship diary.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        InternshipApplication internshipToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.deleteInternshipApplication(internshipToDelete);

        String feedback = internshipToDelete.toString();

        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check

    }

}
