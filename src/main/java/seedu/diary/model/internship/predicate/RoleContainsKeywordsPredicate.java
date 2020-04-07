package seedu.diary.model.internship.predicate;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.List;
import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code Role} contains any of the keywords given.
 */
public class RoleContainsKeywordsPredicate implements Predicate<InternshipApplication> {
    private final List<String> keywords;

    public RoleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream()
            .anyMatch(keyword -> internshipApplication.getRole().fullRole.toLowerCase()
                .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RoleContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((RoleContainsKeywordsPredicate) other).keywords)); // state check
    }

    public boolean isNull() {
        return keywords == null;
    }

    @Override
    public String toString() {
        if (isNull()) {
            return PREFIX_ROLE.toString();
        }
        return PREFIX_ROLE + String.join(" ", keywords);
    }
}
