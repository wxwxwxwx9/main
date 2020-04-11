package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InternshipApplicationBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RoleContainsKeywordsPredicate firstPredicate =
            new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        RoleContainsKeywordsPredicate secondPredicate =
            new RoleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy =
            new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        RoleContainsKeywordsPredicate predicate =
            new RoleContainsKeywordsPredicate(Collections.singletonList("Cleaner"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withRole("Cleaner").build()));

        // Multiple keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Software", "Engineer"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withRole("Software Engineer").build()));

        // Only one matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Software", "Cleaner"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withRole("Software Developer").build()));

        // Mixed-case keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("sOfTwArE", "eNgInEeR"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withRole("Software Engineer").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new InternshipApplicationBuilder().withRole("Software Engineer").build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Cleaner"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withRole("Software Engineer").build()));

        // Keywords match company, phone, email, diary, priority and status, but does not match role
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Google", "12345", "alice@email.com", "Main",
            "Street", "1", "APPLIED"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withCompany("Google")
            .withRole("Software Engineer").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").withPriority("1")
            .withStatus("APPLIED").build()));
    }

    @Test
    public void isNull_nullKeyword_returnsTrue() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(null);
        assertTrue(predicate.isNull());
    }

    @Test
    public void isNull_nonNullKeyword_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Engineer"));
        assertFalse(predicate.isNull());
    }
}
