package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an InternshipApplication's priority in the internship diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(int)}
 */
public class Priority {
    public static final int HIGHEST_PRIORITY = 10;
    public static final int LOWEST_PRIORITY = 0;
    public static final String MESSAGE_CONSTRAINTS =
            "Priority should only contain a number, and it should not be blank";

    public final int fullPriority;

    /**
     * Constructs a {@code Company}.
     *
     * @param priority A valid priority.
     */
    public Priority(int priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        fullPriority = priority;
    }

    /**
     * Returns true if a given string is a valid company.
     */
    public static boolean isValidPriority(int test) {
        return test >= LOWEST_PRIORITY && test <= HIGHEST_PRIORITY;
    }


    @Override
    public String toString() {
        return String.valueOf(fullPriority);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Company // instanceof handles nulls
                && fullPriority == ((Priority) other).fullPriority); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(fullPriority).hashCode();
    }
}
