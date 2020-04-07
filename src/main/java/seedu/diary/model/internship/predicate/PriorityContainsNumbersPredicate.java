package seedu.diary.model.internship.predicate;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.List;
import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code Priority} matches any of the numbers given.
 */
public class PriorityContainsNumbersPredicate implements Predicate<InternshipApplication> {
    private final List<String> numbers;

    public PriorityContainsNumbersPredicate(List<String> numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (numbers == null) {
            return true;
        }

        return numbers.stream()
            .anyMatch(number -> Integer.toString(internshipApplication.getPriority().fullPriority)
                .toLowerCase().equals(number));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PriorityContainsNumbersPredicate // instanceof handles nulls
            && numbers.equals(((PriorityContainsNumbersPredicate) other).numbers)); // state check
    }

    public boolean isNull() {
        return numbers == null;
    }

    @Override
    public String toString() {
        if (isNull()) {
            return PREFIX_PRIORITY.toString();
        }
        return PREFIX_PRIORITY + String.join(" ", numbers);
    }
}
