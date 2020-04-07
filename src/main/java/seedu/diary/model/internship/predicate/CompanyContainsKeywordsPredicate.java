package seedu.diary.model.internship.predicate;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;

import java.util.List;
import java.util.function.Predicate;

import seedu.diary.model.internship.InternshipApplication;

/**
 * Tests that a {@code InternshipApplication}'s {@code Company} contains any of the keywords given.
 */
public class CompanyContainsKeywordsPredicate implements Predicate<InternshipApplication> {
    private final List<String> keywords;

    public CompanyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream()
            .anyMatch(keyword -> internshipApplication.getCompany().fullCompany.toLowerCase()
                .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CompanyContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((CompanyContainsKeywordsPredicate) other).keywords)); // state check
    }

    public boolean isNull() {
        return keywords == null;
    }

    @Override
    public String toString() {
        if (isNull()) {
            return PREFIX_COMPANY.toString();
        }
        return PREFIX_COMPANY + String.join(" ", keywords);
    }
}
