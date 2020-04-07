package seedu.diary.model.internship.predicate;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code Status} contains any of the keywords given.
 */
public class StatusContainsKeywordsPredicate implements Predicate<InternshipApplication> {
    private final List<String> keywords;

    public StatusContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream()
            .anyMatch(keyword -> internshipApplication.getStatus().name().toLowerCase()
                .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StatusContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((StatusContainsKeywordsPredicate) other).keywords)); // state check
    }

    public boolean isNull() {
        return keywords == null;
    }

    @Override
    public String toString() {
        if (isNull()) {
            return PREFIX_STATUS.toString();
        }
        return PREFIX_STATUS + String.join(" ", keywords);
    }
}
