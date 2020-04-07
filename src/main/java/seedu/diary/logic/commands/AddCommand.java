package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Adds an internship application to the internship diary.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds an internship application to the internship diary.\n"
        + "Parameters: "
        + PREFIX_COMPANY + "COMPANY "
        + PREFIX_ROLE + "ROLE "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + PREFIX_DATE + "APPLICATION DATE "
        + "[" + PREFIX_PRIORITY + "PRIORITY] "
        + PREFIX_STATUS + "STATUS "
        + "\nExample: " + COMMAND_WORD + " "
        + PREFIX_COMPANY + "Google "
        + PREFIX_ROLE + "Software Engineer "
        + PREFIX_ADDRESS + "123 Kent Ridge Road "
        + PREFIX_PHONE + "67654321 "
        + PREFIX_EMAIL + "example@google.com "
        + PREFIX_DATE + "10 01 2020 "
        + PREFIX_PRIORITY + "5 "
        + PREFIX_STATUS + "APPLIED";

    public static final String MESSAGE_SUCCESS = "New internship application added: %1$s";
    public static final String MESSAGE_DUPLICATE_INTERNSHIP =
        "This internship application already exists in the internship diary";

    private final InternshipApplication toAdd;

    /**
     * Creates an AddCommand to add the specified {@code internshipApplication}
     */
    public AddCommand(InternshipApplication internshipApplication) {
        requireNonNull(internshipApplication);
        toAdd = internshipApplication;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasInternshipApplication(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
        }

        model.addInternshipApplication(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCommand // instanceof handles nulls
            && toAdd.equals(((AddCommand) other).toAdd));
    }
}
