package seedu.diary.testutil;

import java.util.Arrays;

import seedu.diary.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.StatusContainsKeywordsPredicate;

/**
 * A utility class to help create predicates.
 */
public class PredicateUtil {

    /**
     * Parses {@code userInput} into a {@code CompanyContainsKeywordsPredicate}.
     */
    public static CompanyContainsKeywordsPredicate prepareCompanyPredicate(String userInput) {
        return new CompanyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code RoleContainsKeywordsPredicate}.
     */
    public static RoleContainsKeywordsPredicate prepareRolePredicate(String userInput) {
        return new RoleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    public static AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsNumbersPredicate}.
     */
    public static PhoneContainsNumbersPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsNumbersPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    public static EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PriorityContainsNumbersPredicate}.
     */
    public static PriorityContainsNumbersPredicate preparePriorityPredicate(String userInput) {
        return new PriorityContainsNumbersPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code StatusContainsKeywordsPredicate}.
     */
    public static StatusContainsKeywordsPredicate prepareStatusPredicate(String userInput) {
        return new StatusContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
