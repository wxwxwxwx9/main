package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Unarchives an internship application identified using its displayed index from the internship diary.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Unarchives the internship application "
        + "identified by the index number used in the displayed internship list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNARCHIVE_INTERNSHIP_SUCCESS = "Unarchived Internship Application: %1$s";
    public static final String MESSAGE_ALREADY_UNARCHIVED = "Internship Application already unarchived!";

    private final Index targetIndex;

    public UnarchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        InternshipApplication internshipToUnarchive = lastShownList.get(targetIndex.getZeroBased());

        if (!internshipToUnarchive.isArchived()) {
            throw new CommandException(MESSAGE_ALREADY_UNARCHIVED);
        }

        model.unarchiveInternshipApplication(internshipToUnarchive);

        String feedback = internshipToUnarchive.toString();

        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UnarchiveCommand // instanceof handles nulls
            && targetIndex.equals(((UnarchiveCommand) other).targetIndex)); // state check
    }
}
