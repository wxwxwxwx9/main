package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InternshipApplicationBuilder;

public class PriorityContainsNumbersPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1");
        List<String> secondPredicateKeywordList = Arrays.asList("2", "8");

        PriorityContainsNumbersPredicate firstPredicate =
            new PriorityContainsNumbersPredicate(firstPredicateKeywordList);
        PriorityContainsNumbersPredicate secondPredicate =
            new PriorityContainsNumbersPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PriorityContainsNumbersPredicate firstPredicateCopy =
            new PriorityContainsNumbersPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different numbers -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_priorityContainsNumbers_returnsTrue() {
        // Full number
        PriorityContainsNumbersPredicate predicate =
            new PriorityContainsNumbersPredicate(Collections.singletonList("10"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withPriority("10").build()));

        // One number
        predicate = new PriorityContainsNumbersPredicate(Arrays.asList("1"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withPriority("1").build()));

        // Only one matching number
        predicate = new PriorityContainsNumbersPredicate(Arrays.asList("1", "9"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withPriority("1").build()));
    }

    @Test
    public void test_priorityDoesNotContainNumbers_returnsFalse() {
        // Zero numbers
        PriorityContainsNumbersPredicate predicate = new PriorityContainsNumbersPredicate(Collections.emptyList());
        assertFalse(predicate.test(new InternshipApplicationBuilder().withPriority("1").build()));

        // Non-matching keyword
        predicate = new PriorityContainsNumbersPredicate(Arrays.asList("10"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withPriority("1").build()));

        // Keywords match company, role, phone, email, diary and status but does not match priority
        predicate = new PriorityContainsNumbersPredicate(Arrays.asList("Google", "Software", "Engineer",
            "12345", "alice@email.com", "Main", "Street", "APPLIED"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withCompany("Google")
            .withRole("Software Engineer").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").withPriority("1")
            .withStatus("APPLIED").build()));
    }

    @Test
    public void isNull_nullNumbers_returnsTrue() {
        PriorityContainsNumbersPredicate predicate = new PriorityContainsNumbersPredicate(null);
        assertTrue(predicate.isNull());
    }

    @Test
    public void isNull_nonNullNumbers_returnsFalse() {
        PriorityContainsNumbersPredicate predicate = new PriorityContainsNumbersPredicate(Arrays.asList("1"));
        assertFalse(predicate.isNull());
    }
}
