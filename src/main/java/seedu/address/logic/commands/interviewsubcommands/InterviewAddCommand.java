package seedu.address.logic.commands.interviewsubcommands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.interview.Interview;

public class InterviewAddCommand extends InterviewCommand {
    public static final String MESSAGE_SUCCESS = "New Interview Added: %1$s";

    private final Index index;
    private final Interview toAdd;

    public InterviewAddCommand(Index index, Interview interview) {
        this.index = index;
        toAdd = interview;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
