package seedu.address.logic.commands.interviewsubcommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_ONLINE;

public class InterviewEditCommand extends InterviewCommand {
    public static final String MESSAGE_USAGE = "Edits an Interview from an Internship Application "
            + "by using an index of the internship application, followed by an index of interview to be edited.\n"
            + "Parameters: INDEX(index of internship application) edit INDEX (index of interview to be edited) "
            + "[" + PREFIX_IS_ONLINE + "is it an online interview (true/false)] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS (optional if online interview] "
            + "Example: " + COMMAND_WORD + " 1 edit "
            + PREFIX_IS_ONLINE + "false "
            + PREFIX_ADDRESS + "123 road "
            + PREFIX_DATE + "01 02 2020 ";;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
