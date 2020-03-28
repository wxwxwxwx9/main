package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.ApplicationDateIsDatePredicate;
import seedu.address.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.address.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Function which takes in keywords and outputs predicates of the keyword.
     */
    @FunctionalInterface
    private interface PredicateFunction {
        Predicate<InternshipApplication> apply(List<String> t) throws ParseException;
    }

    private static final Map<Prefix, PredicateFunction> predicateMap;
    private static final Prefix[] acceptedPrefixes;

    static {
        predicateMap = Map.of(
                PREFIX_COMPANY, CompanyContainsKeywordsPredicate::new,
                PREFIX_ROLE, RoleContainsKeywordsPredicate::new,
                PREFIX_ADDRESS, AddressContainsKeywordsPredicate::new,
                PREFIX_PHONE, PhoneContainsNumbersPredicate::new,
                PREFIX_EMAIL, EmailContainsKeywordsPredicate::new,
                PREFIX_DATE, ApplicationDateIsDatePredicate::new,
                PREFIX_PRIORITY, PriorityContainsNumbersPredicate::new,
                PREFIX_STATUS, StatusContainsKeywordsPredicate::new
        );
        acceptedPrefixes = new Prefix[]{PREFIX_COMPANY, PREFIX_ROLE, PREFIX_ADDRESS, PREFIX_PHONE,
            PREFIX_EMAIL, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_STATUS};
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, acceptedPrefixes);

        if (!areAnyPrefixesPresent(argMultimap, acceptedPrefixes) && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Predicate<InternshipApplication>> predicates = new ArrayList<>();
        if (!argMultimap.getPreamble().isEmpty()) {
            List<String> preamble = Arrays.asList(argMultimap.getPreamble().split("\\s+"));
            for (Prefix prefix : acceptedPrefixes) {
                if (prefix.equals(PREFIX_DATE)) {
                    continue;
                }
                predicates.add(predicateMap.get(prefix).apply(preamble));
            }
            return new FindCommand(predicates, true);
        }

        for (Prefix prefix : acceptedPrefixes) {
            if (argMultimap.getValue(prefix).isEmpty()) {
                continue;
            }
            String[] keywords = argMultimap.getValue(prefix).get().split("\\s+");
            predicates.add(predicateMap.get(prefix).apply(Arrays.asList(keywords)));
        }
        return new FindCommand(predicates, false);
    }

    /**
     * Returns true if not all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
