package seedu.diary.logic.util;

import static java.util.Objects.requireNonNull;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.diary.logic.parser.ArgumentMultimap;
import seedu.diary.logic.parser.Prefix;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.StatusContainsKeywordsPredicate;
import seedu.diary.model.status.Status;

/**
 * Contains utility methods used for prefixes in the various *Parser classes.
 */
public class PrefixPredicateUtil {

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
     * To execute a predicate lazily.
     */
    @FunctionalInterface
    public interface PredicateFunction {
        Predicate<InternshipApplication> apply(List<String> t) throws ParseException;
    }

    /**
     * Retrieves the value of the prefix from argument multimap
     * and packages it into a predicate for internship application.
     * Checks if the user input is a valid status.
     *
     * @param argMultimap argument multimap to extract the prefix for predicate creation.
     * @return predicate to filter internship application list.
     * @throws ParseException if the user input does not conform the expected format.
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
        requireNonNull(keywords);
        keywords = filterValidStatuses(keywords);
        Predicate<InternshipApplication> predicate = PREDICATE_MAP.get(selectedPrefix).apply(keywords);
        return predicate;
    }

    /**
     * Checks if any of the user keywords contain valid statuses.
     *
     * @param keywords to check for valid statuses.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private static List<String> filterValidStatuses(List<String> keywords) throws ParseException {
        List<String> validKeywords = keywords.stream()
            .filter(Status::isValidStatus)
            .collect(Collectors.toList());
        if (validKeywords.isEmpty()) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return validKeywords;
    }

}
