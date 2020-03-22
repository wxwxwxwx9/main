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
import seedu.address.model.internship.ApplicationDateIsDatePredicate;
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.PriorityContainsNumbersPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.StatusContainsKeywordsPredicate;

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
    private final ApplicationDateIsDatePredicate dPredicate;
    private final PriorityContainsNumbersPredicate wPredicate;
    private final StatusContainsKeywordsPredicate sPredicate;
    private final List<Predicate<InternshipApplication>> predicates = new ArrayList<Predicate<InternshipApplication>>();
    private final boolean isPreamble;

    public FindCommand(CompanyContainsKeywordsPredicate cPredicate) {
        this.cPredicate = cPredicate;
        this.rPredicate = new RoleContainsKeywordsPredicate(null);
        this.aPredicate = new AddressContainsKeywordsPredicate(null);
        this.pPredicate = new PhoneContainsNumbersPredicate(null);
        this.ePredicate = new EmailContainsKeywordsPredicate(null);
        this.dPredicate = new ApplicationDateIsDatePredicate(null);
        this.wPredicate = new PriorityContainsNumbersPredicate(null);
        this.sPredicate = new StatusContainsKeywordsPredicate(null);
        this.isPreamble = false;
    }

    public FindCommand(CompanyContainsKeywordsPredicate cPredicate, RoleContainsKeywordsPredicate rPredicate,
                       AddressContainsKeywordsPredicate aPredicate, PhoneContainsNumbersPredicate pPredicate,
                       EmailContainsKeywordsPredicate ePredicate, ApplicationDateIsDatePredicate dPredicate,
                       PriorityContainsNumbersPredicate wPredicate, StatusContainsKeywordsPredicate sPredicate,
                       boolean isPreamble) {
        this.cPredicate = cPredicate;
        this.rPredicate = rPredicate;
        this.aPredicate = aPredicate;
        this.pPredicate = pPredicate;
        this.ePredicate = ePredicate;
        this.dPredicate = dPredicate;
        this.wPredicate = wPredicate;
        this.sPredicate = sPredicate;
        this.isPreamble = isPreamble;
        if (!cPredicate.isNull()) {
            predicates.add(cPredicate);
        }
        if (!rPredicate.isNull()) {
            predicates.add(rPredicate);
        }
        if (!aPredicate.isNull()) {
            predicates.add(aPredicate);
        }
        if (!pPredicate.isNull()) {
            predicates.add(pPredicate);
        }
        if (!ePredicate.isNull()) {
            predicates.add(ePredicate);
        }
        if (!dPredicate.isNull()) {
            predicates.add(dPredicate);
        }
        if (!wPredicate.isNull()) {
            predicates.add(wPredicate);
        }
        if (!sPredicate.isNull()) {
            predicates.add(sPredicate);
        }
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
                && ePredicate.equals(((FindCommand) other).ePredicate)
                && dPredicate.equals(((FindCommand) other).dPredicate)
                && wPredicate.equals(((FindCommand) other).wPredicate)
                && sPredicate.equals(((FindCommand) other).sPredicate)
                && predicates.equals(((FindCommand) other).predicates)
                && isPreamble == ((FindCommand) other).isPreamble); // state check
    }
}
