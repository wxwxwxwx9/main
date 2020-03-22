package seedu.address.logic.commands.interviewsubcommands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

import static java.util.Objects.requireNonNull;

public class InterviewAddCommand extends InterviewCommand {
    public static final String MESSAGE_SUCCESS = "New Interview added: %1$s";
    public static final String MESSAGE_DUPLICATE_INTERVIEW =
            "This interview already exists in the internship application: %1$s";

    private final Index index;
    private final Interview toAdd;

    public InterviewAddCommand(Index index, Interview interview) {
        this.index = index;
        toAdd = interview;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        InternshipApplication internshipToModify = super.getInternshipApplication(model, index);

        if (internshipToModify.hasInterview(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_INTERVIEW, internshipToModify));
        }

        internshipToModify.addInterview(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
