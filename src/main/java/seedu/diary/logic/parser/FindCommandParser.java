package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.diary.logic.commands.FindCommand;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.ApplicationDateIsDatePredicate;
import seedu.diary.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Prefix[] ACCEPTED_PREFIXES = new Prefix[]{PREFIX_COMPANY, PREFIX_ROLE, PREFIX_ADDRESS,
        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_STATUS};

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, ACCEPTED_PREFIXES);

        if (!areAnyPrefixesPresent(argMultimap, ACCEPTED_PREFIXES)
            && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            List<Predicate<InternshipApplication>> predicates = new ArrayList<>();
            String[] preamble = argMultimap.getPreamble().split("\\s+");
            predicates.add(new CompanyContainsKeywordsPredicate(Arrays.asList(preamble)));
            predicates.add(new RoleContainsKeywordsPredicate(Arrays.asList(preamble)));
            predicates.add(new AddressContainsKeywordsPredicate(Arrays.asList(preamble)));
            predicates.add(new PhoneContainsNumbersPredicate(Arrays.asList(preamble)));
            predicates.add(new EmailContainsKeywordsPredicate(Arrays.asList(preamble)));
            predicates.add(new PriorityContainsNumbersPredicate(Arrays.asList(preamble)));
            predicates.add(new StatusContainsKeywordsPredicate(Arrays.asList(preamble)));
            return new FindCommand(predicates, true);
        }

        return new FindCommand(getRequiredPredicates(argMultimap, ACCEPTED_PREFIXES), false);
    }

    /**
     * Returns true if not all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns a list of predicate that is required based on the prefixes.
     *
     * @param argumentMultimap the multimap of all the argument and prefixes
     * @param prefixes the valid prefixes to check
     * @return the list of predicates based on the prefixes
     * @throws ParseException if the user input does not conform the expected format
     */
    private static List<Predicate<InternshipApplication>> getRequiredPredicates(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) throws ParseException {
        List<Predicate<InternshipApplication>> predicates = new ArrayList<>();
        for (Prefix p : prefixes) {
            if (!argumentMultimap.getValue(p).isPresent()) {
                continue;
            }
            String[] arguments = argumentMultimap.getValue(p).get().split("\\s+");
            Predicate<InternshipApplication> predicate = getPredicate(p, arguments);
            assert (predicate != null);
            predicates.add(predicate);
        }
        return predicates;
    }

    /**
     * Gets the correct predicate for a given prefix.
     *
     * @param prefix the prefix that you need the predicate for
     * @param arguments the arguments of the command for the prefix
     * @return the predicate corresponding to the prefix
     * @throws ParseException if the user input does not conform the expected format
     */
    private static Predicate<InternshipApplication> getPredicate(Prefix prefix,
        String[] arguments) throws ParseException {
        if (prefix.equals(PREFIX_COMPANY)) {
            return new CompanyContainsKeywordsPredicate(Arrays.asList(arguments));
        } else if (prefix.equals(PREFIX_ROLE)) {
            return new RoleContainsKeywordsPredicate(Arrays.asList(arguments));
        } else if (prefix.equals(PREFIX_ADDRESS)) {
            return new AddressContainsKeywordsPredicate(Arrays.asList(arguments));
        } else if (prefix.equals(PREFIX_PHONE)) {
            return new PhoneContainsNumbersPredicate(Arrays.asList(arguments));
        } else if (prefix.equals(PREFIX_EMAIL)) {
            return new EmailContainsKeywordsPredicate(Arrays.asList(arguments));
        } else if (prefix.equals(PREFIX_DATE)) {
            return new ApplicationDateIsDatePredicate(Arrays.asList(arguments));
        } else if (prefix.equals(PREFIX_PRIORITY)) {
            return new PriorityContainsNumbersPredicate(Arrays.asList(arguments));
        } else if (prefix.equals(PREFIX_STATUS)) {
            return new StatusContainsKeywordsPredicate(Arrays.asList(arguments));
        } else {
            //should not happen.
            return null;
        }
    }
}
