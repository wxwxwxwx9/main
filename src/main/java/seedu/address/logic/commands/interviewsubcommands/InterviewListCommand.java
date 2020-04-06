package seedu.address.logic.commands.interviewsubcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;

/**
 * Lists all interviews inside an Internship Application.
 */
public class InterviewListCommand extends InterviewCommand {
    public static final String MESSAGE_SUCCESS = "listed all interviews in %1$s";

    private Index index;

    public InterviewListCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        InternshipApplication internshipToList = super.getInternshipApplication(model, index);
        // Todo: change interviews into observable list and make it show here.
        return new CommandResult(String.format(MESSAGE_SUCCESS, internshipToList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof InterviewListCommand
            && index.equals(((InterviewListCommand) other).index));
    }
}
