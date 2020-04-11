package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InternshipApplicationBuilder;

public class StatusContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        StatusContainsKeywordsPredicate firstPredicate =
            new StatusContainsKeywordsPredicate(firstPredicateKeywordList);
        StatusContainsKeywordsPredicate secondPredicate =
            new StatusContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StatusContainsKeywordsPredicate firstPredicateCopy =
            new StatusContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_statusContainsKeywords_returnsTrue() {
        // Full status
        StatusContainsKeywordsPredicate predicate =
            new StatusContainsKeywordsPredicate(Collections.singletonList("APPLIED"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withStatus("APPLIED").build()));

        // One keyword
        predicate = new StatusContainsKeywordsPredicate(Collections.singletonList("APPLIED"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withStatus("APPLIED").build()));

        // Only one matching keyword
        predicate = new StatusContainsKeywordsPredicate(Arrays.asList("APPLIED", "REJECTED"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withStatus("APPLIED").build()));

        // Mixed-case keywords
        predicate = new StatusContainsKeywordsPredicate(Arrays.asList("aPpLiEd"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withStatus("APPLIED").build()));
    }

    @Test
    public void test_statusDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        StatusContainsKeywordsPredicate predicate = new StatusContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus("APPLIED").build()));

        // Non-matching keyword
        predicate = new StatusContainsKeywordsPredicate(Arrays.asList("bob"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withStatus("APPLIED").build()));

        // Keywords match company, role, phone, email, diary and priority, but does not match status
        predicate = new StatusContainsKeywordsPredicate(Arrays.asList("Google", "Software", "Engineer", "12345",
            "alice@email.com", "Main", "Street", "1"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withCompany("Google")
            .withRole("Software Engineer").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").withPriority("1")
            .withStatus("APPLIED").build()));
    }

    @Test
    public void isNull_nullKeyword_returnsTrue() {
        StatusContainsKeywordsPredicate predicate = new StatusContainsKeywordsPredicate(null);
        assertTrue(predicate.isNull());
    }

    @Test
    public void isNull_nonNullKeyword_returnsFalse() {
        StatusContainsKeywordsPredicate predicate = new StatusContainsKeywordsPredicate(Arrays.asList("APPLIED"));
        assertFalse(predicate.isNull());
    }
}
