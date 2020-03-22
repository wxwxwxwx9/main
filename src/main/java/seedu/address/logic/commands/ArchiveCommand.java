package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;

/**
 * Archives an internship application identified using it's displayed index from the internship diary.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the internship application "
            + "identified by the index number used in the displayed internship list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_INTERNSHIP_SUCCESS = "Archived Internship Application: %1$s";

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

        // implement guard clause to handle case where archiving already archived internship

        InternshipApplication internshipToArchive = lastShownList.get(targetIndex.getZeroBased());
        InternshipApplication editedInternship = new InternshipApplication(
            internshipToArchive.getCompany(),
            internshipToArchive.getRole(),
            internshipToArchive.getAddress(),
            internshipToArchive.getPhone(),
            internshipToArchive.getEmail(),
            internshipToArchive.getApplicationDate(),
            internshipToArchive.getPriority(),
            internshipToArchive.getStatus(),
            true
        );
        // implementation is questionable, will relook into how I can improve this
        // basically idea is just to deep clone the internshipToArchive and set isArchived to true
        // initially thought of using edit command, but it's too cumbersome
        // add, delete, setInternshipApplication updates the UI automatically
        // no need to go through manual filtering (like below)
        model.setInternshipApplication(internshipToArchive, editedInternship);

        // internshipToArchive.archive();
        // need PREDICATE_SHOW_ALL as a buffer before SHOW_NOT_ARCHIVED
        // without show all, show not archived won't work, for some yet unknown reason
        // model.updateFilteredInternshipApplicationList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        // model.updateFilteredInternshipApplicationList(PREDICATE_SHOW_NOT_ARCHIVED_INTERNSHIPS);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_INTERNSHIP_SUCCESS, internshipToArchive));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveCommand // instanceof handles nulls
                && targetIndex.equals(((ArchiveCommand) other).targetIndex)); // state check
    }
}
