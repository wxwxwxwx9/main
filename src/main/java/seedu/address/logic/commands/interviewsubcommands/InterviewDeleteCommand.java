package seedu.address.logic.commands.interviewsubcommands;

import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

/**
 * Deletes an interview from an Internship Application.
 */
public class InterviewDeleteCommand extends InterviewCommand {
    public static final String MESSAGE_SUCCESS = "Deleted Interview: %1$s";
    public static final String MESSAGE_USAGE = "Deletes an Interview from an Internship Application "
            + "by using an index of the internship application, followed by an index of interview to be deleted.\n"
            + "Parameters: INDEX(index of internship application) delete INDEX (index of interview to be deleted). "
            + "Example: " + COMMAND_WORD + " 1 delete 1";

    private Index internshipIndex;
    private Index interviewIndex;

    public InterviewDeleteCommand(Index internshipIndex, Index interviewIndex) {
        this.internshipIndex = internshipIndex;
        this.interviewIndex = interviewIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        InternshipApplication internshipToModify = super.getInternshipApplication(model, internshipIndex);
        ArrayList<Interview> interviews = internshipToModify.getInterviews();

        if (interviewIndex.getZeroBased() >= interviews.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToDelete = interviews.get(interviewIndex.getZeroBased());
        interviews.remove(interviewIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, interviewToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof InterviewDeleteCommand
                && interviewIndex.equals(((InterviewDeleteCommand) other).interviewIndex)
                && internshipIndex.equals(((InterviewDeleteCommand) other).internshipIndex));
    }
}
