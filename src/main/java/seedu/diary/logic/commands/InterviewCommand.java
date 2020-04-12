package seedu.diary.logic.commands;

import java.util.List;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.interview.Interview;

/**
 * Represents an abstract interview command that modifies interviews in an Internship Application.
 */
public abstract class InterviewCommand extends Command {

    public static final String COMMAND_WORD = "interview";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Modifies Interviews in an Internship Application by using an index to specify application followed"
        + "by a command word to specify action to be taken.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "COMMAND_CODE (must be either add, edit, delete, or list) "
        + "other parameters as defined by the command code. "
        + "type help or interview INDEX COMMAND_CODE to find out the respective required parameters.";
    public static final String MESSAGE_INTERVIEW_DATE_ERROR = "Interview Date should not "
        + "be before Internship Application Date.";

    protected InternshipApplication getInternshipApplication(Model model, Index index) throws CommandException {
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Checks if interviewDate is before applicationDate
     */
    protected boolean isInterviewBeforeApplication(InternshipApplication internshipApplication, Interview interview) {
        return interview.getInterviewDate()
            .compareTo(internshipApplication.getApplicationDate().fullApplicationDate) < 0;
    }
}
