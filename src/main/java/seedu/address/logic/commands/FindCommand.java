package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.CustomToStringPredicate;

/**
 * Finds and lists all internship applications in internship diary
 * whose fields contains any of the field's argument keywords.
 * Keyword matching is case insensitive.
 * Email and Phone matching is based on whether there is a substring that matches.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Finds all internship applications whose fields contain all of "
        + "the specified field keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: "
        + "[KEYWORDS] "
        + "[" + PREFIX_COMPANY + "COMPANY] "
        + "[" + PREFIX_ROLE + "ROLE] "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_PRIORITY + "PRIORITY] "
        + "[" + PREFIX_STATUS + "STATUS] "
        + "Example: " + COMMAND_WORD + " c/Google r/Engineer";

    private final List<Predicate<InternshipApplication>> predicates;
    private final boolean isPreamble;

    public FindCommand(List<Predicate<InternshipApplication>> predicates, boolean isPreamble) {
        this.predicates = predicates;
        this.isPreamble = isPreamble;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<InternshipApplication> predicate;
        if (isPreamble) {
            predicate = predicates.stream().reduce(x -> false, Predicate::or);
        } else {
            predicate = predicates.stream().reduce(x -> true, Predicate::and);
        }
        predicate = new CustomToStringPredicate<>(predicate, this.toString());
        model.updateFilteredInternshipApplicationList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_INTERNSHIP_LISTED_OVERVIEW,
                model.getFilteredInternshipApplicationList().size()));
    }

    @Override
    public boolean equals(Object other) {

        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && predicates.equals(((FindCommand) other).predicates)
            && isPreamble == ((FindCommand) other).isPreamble); // state check
    }

    @Override
    public String toString() {
        String delimiter = isPreamble ? " OR " : " AND ";
        return predicates.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }
}
