package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code InternshipApplication}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsNumbersPredicate implements Predicate<InternshipApplication> {
    private final List<String> numbers;

    public PhoneContainsNumbersPredicate(List<String> numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (numbers == null) {
            return true;
        }

        return numbers.stream()
                .anyMatch(number -> internshipApplication.getPhone().value.toLowerCase().contains(number));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsNumbersPredicate // instanceof handles nulls
                && numbers.equals(((PhoneContainsNumbersPredicate) other).numbers)); // state check
    }
}
