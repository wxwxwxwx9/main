package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InternshipApplicationBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EmailContainsKeywordsPredicate firstPredicate =
            new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate =
            new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
            new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // Full email
        EmailContainsKeywordsPredicate predicate =
            new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withEmail("alice@example.com").build()));

        // One keyword
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("alice@"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("bob", "example"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withEmail("bob@example.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice", "hello"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withEmail("alice@example.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("aLiCe", "eXaMpLe"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new InternshipApplicationBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("bob"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withEmail("alice@example.com").build()));

        // Keywords match company, role, phone, diary, priority and status, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Google", "Software", "Engineer", "12345", "Main",
            "Street", "1", "APPLIED"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withCompany("Google")
            .withRole("Software Engineer").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").withPriority("1")
            .withStatus("APPLIED").build()));
    }

    @Test
    public void isNull_nullKeywords_returnsTrue() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(null);
        assertTrue(predicate.isNull());
    }

    @Test
    public void isNull_nonNullKeywords_returnsFalse() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice"));
        assertFalse(predicate.isNull());
    }
}
