package seedu.address.model.internship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.InternshipApplicationBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Clementi"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withAddress("Clementi").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Main", "Street"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withAddress("Main Street").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Main", "Avenue"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withAddress("Main Road").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("mAiN", "sTrEeT"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withAddress("Main Street").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate
                .test(new InternshipApplicationBuilder().withAddress("Blk 456, Den Road, #01-355").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Clementi"));
        assertFalse(predicate
                .test(new InternshipApplicationBuilder().withAddress("Blk 456, Den Road, #01-355").build()));

        // Keywords match company, role, phone and email, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Google", "Software",
                "Engineer", "12345", "alice@email.com"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withCompany("Google")
                .withRole("Software Engineer").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
