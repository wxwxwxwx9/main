package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Archives an internship application identified using it's displayed index from the internship diary.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the internship application "
            + "identified by the index number used in the displayed internship list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNARCHIVE_INTERNSHIP_SUCCESS = "Unarchived Internship Application: %1$s";

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

        // implement guard clause to handle case where archiving already archived internship

        InternshipApplication internshipToUnarchive = lastShownList.get(targetIndex.getZeroBased());
        InternshipApplication editedInternship = new InternshipApplication(
            internshipToUnarchive.getCompany(),
            internshipToUnarchive.getRole(),
            internshipToUnarchive.getAddress(),
            internshipToUnarchive.getPhone(),
            internshipToUnarchive.getEmail(),
            internshipToUnarchive.getApplicationDate(),
            internshipToUnarchive.getPriority(),
            internshipToUnarchive.getStatus(),
  false
        );

        model.setInternshipApplication(internshipToUnarchive, editedInternship);

        return new CommandResult(String.format(MESSAGE_UNARCHIVE_INTERNSHIP_SUCCESS, internshipToUnarchive));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnarchiveCommand // instanceof handles nulls
                && targetIndex.equals(((UnarchiveCommand) other).targetIndex)); // state check
    }
}
