package seedu.diary.logic.commands.interviewsubcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.CommandResult;
import seedu.diary.logic.commands.InterviewCommand;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.interview.Interview;

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
        requireNonNull(internshipIndex);
        requireNonNull(interviewIndex);
        this.internshipIndex = internshipIndex;
        this.interviewIndex = interviewIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        InternshipApplication internshipToModify = super.getInternshipApplication(model, internshipIndex);
        List<Interview> interviews = internshipToModify.getInterviews();

        if (interviewIndex.getZeroBased() >= interviews.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToDelete = interviews.get(interviewIndex.getZeroBased());
        internshipToModify.deleteInterview(interviewToDelete);
        model.displayInternshipDetail(internshipToModify);
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
