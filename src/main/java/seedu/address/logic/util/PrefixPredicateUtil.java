package seedu.address.logic.util;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.address.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.StatusContainsKeywordsPredicate;
import seedu.address.model.status.Status;

/**
 * Contains utility methods used for prefixes in the various *Parser classes.
 */
public class PrefixPredicateUtil {

    /**
     * To execute a predicate lazily.
     */
    @FunctionalInterface
    public interface PredicateFunction {
        Predicate<InternshipApplication> apply(List<String> t) throws ParseException;
    }

    /**
     * Prefixes and their mapping to its appropriate predicates.
     */
    public static final Map<Prefix, PredicateFunction> PREDICATE_MAP = Map.of(
        PREFIX_COMPANY, CompanyContainsKeywordsPredicate::new,
        PREFIX_ROLE, RoleContainsKeywordsPredicate::new,
        PREFIX_STATUS, StatusContainsKeywordsPredicate::new,
        PREFIX_PRIORITY, PriorityContainsNumbersPredicate::new,
        PREFIX_ADDRESS, AddressContainsKeywordsPredicate::new,
        PREFIX_EMAIL, EmailContainsKeywordsPredicate::new,
        PREFIX_PHONE, PhoneContainsNumbersPredicate::new
    );

    /**
     * Retrieves the value of the prefix from argument multimap
     * and packages it into a predicate for internship application.
     * Checks if the user input is a valid status.
     *
     * @param argMultimap argument multimap to extract the prefix for predicate creation.
     * @throws ParseException if the user input does not conform the expected format.
     * @returns predicate to filter internship application list.
     */
    public static Predicate<InternshipApplication> getFieldPredicate(ArgumentMultimap argMultimap,
        Prefix[] acceptedPrefixes) throws ParseException {
        List<String> keywords = null;
        Prefix selectedPrefix = null;
        for (Prefix prefix : acceptedPrefixes) {
            if (argMultimap.getValue(prefix).isPresent()) {
                String input = argMultimap.getValue(prefix).get();
                selectedPrefix = prefix;
                keywords = Arrays.asList(input.split("\\s+"));
                break;
            }
        }
        checkForValidStatuses(keywords);
        Predicate<InternshipApplication> predicate = PREDICATE_MAP.get(selectedPrefix).apply(keywords);
        return predicate;
    }

    /**
     * Checks if any of the user keywords contain valid statuses.
     *
     * @param keywords to check for valid statuses.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private static void checkForValidStatuses(List<String> keywords) throws ParseException {
        keywords = keywords.stream()
            .filter(keyword -> Status.isValidStatus(keyword))
            .collect(Collectors.toList());
        if (keywords.isEmpty()) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
    }

}
