package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code InternshipApplication}'s {@code Address} contains any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<InternshipApplication> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(InternshipApplication internshipApplication) {
        if (keywords == null) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> internshipApplication.getAddress().value.toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

    public boolean isNull() {
        return keywords == null;
    }
}
