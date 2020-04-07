package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Archives an internship application identified using its displayed index from the internship diary.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Archives the internship application "
        + "identified by the index number used in the displayed internship list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_INTERNSHIP_SUCCESS = "Archived Internship Application: %1$s";
    public static final String MESSAGE_ALREADY_ARCHIVED = "Internship Application already archived!";

    private final Index targetIndex;

    public ArchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        InternshipApplication internshipToArchive = lastShownList.get(targetIndex.getZeroBased());

        if (internshipToArchive.isArchived()) {
            throw new CommandException(MESSAGE_ALREADY_ARCHIVED);
        }

        model.archiveInternshipApplication(internshipToArchive);

        String feedback = internshipToArchive.toString();

        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ArchiveCommand // instanceof handles nulls
            && targetIndex.equals(((ArchiveCommand) other).targetIndex)); // state check
    }
}
