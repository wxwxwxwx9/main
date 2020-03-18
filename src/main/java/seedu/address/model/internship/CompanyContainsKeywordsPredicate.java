package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code InternshipApplication}'s {@code Company} matches any of the keywords given.
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
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(internshipApplication.getCompany().fullCompany,
                        keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompanyContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CompanyContainsKeywordsPredicate) other).keywords)); // state check
    }
}
