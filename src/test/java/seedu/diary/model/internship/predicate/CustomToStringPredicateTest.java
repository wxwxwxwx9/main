package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.diary.model.internship.InternshipApplication;

public class CustomToStringPredicateTest {

    @Test
    public void equals() {
        String str1 = "a";
        String str2 = "b";

        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate =
            new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
            new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        CustomToStringPredicate<InternshipApplication> firstPredicateWrap =
            new CustomToStringPredicate<>(firstPredicate, str1);

        CustomToStringPredicate<InternshipApplication> secondPredicateWrap =
            new CustomToStringPredicate<>(secondPredicate, str1);

        CustomToStringPredicate<InternshipApplication> thirdPredicateWrap =
            new CustomToStringPredicate<>(firstPredicate, str2);

        // same object -> returns true
        assertEquals(firstPredicateWrap, firstPredicateWrap);

        // same values -> returns true
        CustomToStringPredicate<InternshipApplication> firstPredicateWrapCopy =
            new CustomToStringPredicate<>(new AddressContainsKeywordsPredicate(firstPredicateKeywordList), str1);
        assertEquals(firstPredicateWrap, firstPredicateWrapCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicateWrap);

        // null -> returns false
        assertNotEquals(null, firstPredicateWrap);

        // different internal predicate -> returns false
        assertNotEquals(firstPredicateWrap, secondPredicateWrap);

        // different internal string -> returns false
        assertNotEquals(firstPredicateWrap, thirdPredicateWrap);
    }

    @Test
    public void test_internalPredicateAndStringIsSame() {
        Object someObject = "a";
        String someString = "b";
        class MockPredicate implements Predicate<Object> {
            @Override
            public boolean test(Object o) {
                return o.equals(someObject);
            }
        }

        CustomToStringPredicate<Object> customToStringPredicate =
            new CustomToStringPredicate<>(new MockPredicate(), someString);

        assertTrue(customToStringPredicate.test(someObject));
        assertFalse(customToStringPredicate.test(someString));
        assertEquals(customToStringPredicate.toString(), someString);
    }
}
