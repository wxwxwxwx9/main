package seedu.diary.model.internship.predicate;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;
import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code Phone} contains any of the numbers given.
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

    public boolean isNull() {
        return numbers == null;
    }

    @Override
    public String toString() {
        if (isNull()) {
            return PREFIX_PHONE.toString();
        }
        return PREFIX_PHONE + String.join(" ", numbers);
    }
}
