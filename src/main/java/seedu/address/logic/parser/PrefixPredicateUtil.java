package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.address.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.StatusContainsKeywordsPredicate;

/**
 * Contains utility methods used for prefixes in the various *Parser classes.
 */
public class PrefixPredicateUtil {

    /** To execute a predicate lazily. */
    @FunctionalInterface
    public interface PredicateFunction {
        Predicate<InternshipApplication> apply(List<String> t) throws ParseException;
    }

    /** Prefixes and their mapping to its appropriate predicates. */
    public static final Map<Prefix, PredicateFunction> PREDICATE_MAP = Map.of(
        PREFIX_COMPANY, CompanyContainsKeywordsPredicate::new,
        PREFIX_ROLE, RoleContainsKeywordsPredicate::new,
        PREFIX_STATUS, StatusContainsKeywordsPredicate::new,
        PREFIX_PRIORITY, PriorityContainsNumbersPredicate::new,
        PREFIX_ADDRESS, AddressContainsKeywordsPredicate::new,
        PREFIX_EMAIL, EmailContainsKeywordsPredicate::new,
        PREFIX_PHONE, PhoneContainsNumbersPredicate::new
    );

}
