package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.InternshipApplicationBuilder;

public class PhoneContainsNumbersPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("12345");
        List<String> secondPredicateKeywordList = Arrays.asList("12345", "67890");

        PhoneContainsNumbersPredicate firstPredicate =
            new PhoneContainsNumbersPredicate(firstPredicateKeywordList);
        PhoneContainsNumbersPredicate secondPredicate =
            new PhoneContainsNumbersPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsNumbersPredicate firstPredicateCopy =
            new PhoneContainsNumbersPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different numbers -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsNumbers_returnsTrue() {
        // Full number
        PhoneContainsNumbersPredicate predicate =
            new PhoneContainsNumbersPredicate(Collections.singletonList("12345"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withPhone("12345").build()));

        // One number
        predicate = new PhoneContainsNumbersPredicate(Arrays.asList("123"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withPhone("123456").build()));

        // Multiple numbers
        predicate = new PhoneContainsNumbersPredicate(Arrays.asList("123", "456"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withPhone("123456").build()));

        // Only one matching number
        predicate = new PhoneContainsNumbersPredicate(Arrays.asList("123", "987"));
        assertTrue(predicate.test(new InternshipApplicationBuilder().withPhone("123456").build()));
    }

    @Test
    public void test_phoneDoesNotContainNumbers_returnsFalse() {
        // Zero numbers
        PhoneContainsNumbersPredicate predicate = new PhoneContainsNumbersPredicate(Collections.emptyList());
        assertFalse(predicate.test(new InternshipApplicationBuilder().withPhone("12345678").build()));

        // Non-matching number
        predicate = new PhoneContainsNumbersPredicate(Arrays.asList("876"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withPhone("12345678").build()));

        // Keywords match company, role, email, diary, priority and status, but does not match phone
        predicate = new PhoneContainsNumbersPredicate(Arrays.asList("Google", "Software", "Engineer",
            "alice@email.com", "Main", "Street", "8", "APPLIED"));
        assertFalse(predicate.test(new InternshipApplicationBuilder().withCompany("Google")
            .withRole("Software Engineer").withPhone("12345")
            .withEmail("alice@email.com").withAddress("Main Street").withPriority("8")
            .withStatus("APPLIED").build()));
    }

    @Test
    public void isNull_nullNumbers_returnsTrue() {
        PhoneContainsNumbersPredicate predicate = new PhoneContainsNumbersPredicate(null);
        assertTrue(predicate.isNull());
    }

    @Test
    public void isNull_nonNullNumbers_returnsFalse() {
        PhoneContainsNumbersPredicate predicate = new PhoneContainsNumbersPredicate(Arrays.asList("12345"));
        assertFalse(predicate.isNull());
    }
}
