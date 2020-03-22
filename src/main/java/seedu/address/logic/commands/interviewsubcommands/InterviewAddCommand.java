package seedu.address.logic.commands.interviewsubcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_ONLINE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

/**
 * Adds an interview into an Internship Application.
 */
public class InterviewAddCommand extends InterviewCommand {
    public static final String MESSAGE_SUCCESS = "New Interview added: %1$s";
    public static final String MESSAGE_DUPLICATE_INTERVIEW =
            "This interview already exists in the internship application: %1$s";
    public static final String MESSAGE_USAGE = "Adds an interview into an Internship Application.\n"
            + "Parameters: INDEX (must be a positive integer) add "
            + "[" + PREFIX_IS_ONLINE + "is it an online interview (true/false)] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS (optional if online interview] "
            + "Example: " + COMMAND_WORD + " 1 add "
            + PREFIX_IS_ONLINE + "false "
            + PREFIX_ADDRESS + "123 road "
            + PREFIX_DATE + "01 02 2020 ";

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

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof InterviewAddCommand
                && toAdd.equals(((InterviewAddCommand) other).toAdd)
                && index.equals(((InterviewAddCommand) other).index));
    }
}
