package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.internship.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;

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
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "Example: " + COMMAND_WORD + " c/Google r/Engineer";

    private final CompanyContainsKeywordsPredicate cPredicate;
    private final RoleContainsKeywordsPredicate rPredicate;
    private final AddressContainsKeywordsPredicate aPredicate;
    private final PhoneContainsNumbersPredicate pPredicate;
    private final EmailContainsKeywordsPredicate ePredicate;

    public FindCommand(CompanyContainsKeywordsPredicate cPredicate) {
        this.cPredicate = cPredicate;
        this.rPredicate = new RoleContainsKeywordsPredicate(null);
        this.aPredicate = new AddressContainsKeywordsPredicate(null);
        this.pPredicate = new PhoneContainsNumbersPredicate(null);
        this.ePredicate = new EmailContainsKeywordsPredicate(null);
    }

    public FindCommand(CompanyContainsKeywordsPredicate cPredicate, RoleContainsKeywordsPredicate rPredicate,
                       AddressContainsKeywordsPredicate aPredicate, PhoneContainsNumbersPredicate pPredicate,
                       EmailContainsKeywordsPredicate ePredicate) {
        this.cPredicate = cPredicate;
        this.rPredicate = rPredicate;
        this.aPredicate = aPredicate;
        this.pPredicate = pPredicate;
        this.ePredicate = ePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Predicate<InternshipApplication>> predicates = new ArrayList<Predicate<InternshipApplication>>();
        predicates.add(cPredicate);
        predicates.add(rPredicate);
        predicates.add(aPredicate);
        predicates.add(pPredicate);
        predicates.add(ePredicate);
        Predicate<InternshipApplication> predicate = predicates.stream().reduce(x -> true, Predicate::and);
        model.updateFilteredInternshipApplicationList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_INTERNSHIP_LISTED_OVERVIEW,
                        model.getFilteredInternshipApplicationList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && cPredicate.equals(((FindCommand) other).cPredicate)
                && rPredicate.equals(((FindCommand) other).rPredicate)
                && aPredicate.equals(((FindCommand) other).aPredicate)
                && pPredicate.equals(((FindCommand) other).pPredicate)
                && ePredicate.equals(((FindCommand) other).ePredicate)); // state check
    }
}
