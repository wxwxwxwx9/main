package seedu.diary.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.diary.commons.util.AppUtil.checkArgument;

/**
 * Represents a InternshipApplication's company in the internship diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company implements Comparable<Company> {

    public static final String MESSAGE_CONSTRAINTS =
        "Companies should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the company must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullCompany;

    /**
     * Constructs a {@code Company}.
     *
     * @param company A valid company.
     */
    public Company(String company) {
        requireNonNull(company);
        checkArgument(isValidCompany(company), MESSAGE_CONSTRAINTS);
        fullCompany = company;
    }

    /**
     * Returns true if a given string is a valid company.
     */
    public static boolean isValidCompany(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullCompany;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Company // instanceof handles nulls
            && fullCompany.equals(((Company) other).fullCompany)); // state check
    }

    @Override
    public int hashCode() {
        return fullCompany.hashCode();
    }

    /**
     * Does string comparison of company name.
     */
    @Override
    public int compareTo(Company other) {
        return fullCompany.compareToIgnoreCase(other.fullCompany); // String comparison.
    }
}
