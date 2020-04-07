package seedu.diary.model.internship.predicate;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;

import java.util.List;
import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code Email} contains any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<InternshipApplication> {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream()
            .anyMatch(keyword -> internshipApplication.getEmail().value.toLowerCase()
                .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

    public boolean isNull() {
        return keywords == null;
    }

    @Override
    public String toString() {
        if (isNull()) {
            return PREFIX_EMAIL.toString();
        }
        return PREFIX_EMAIL + String.join(" ", keywords);
    }
}
