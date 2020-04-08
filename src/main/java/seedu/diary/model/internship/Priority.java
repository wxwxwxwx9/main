package seedu.diary.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.diary.commons.util.AppUtil.checkArgument;

/**
 * Represents an InternshipApplication's priority in the internship diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(int)}
 */
public class Priority implements Comparable<Priority> {
    public static final int HIGHEST_PRIORITY = 10;
    public static final int LOWEST_PRIORITY = 0;
    public static final String MESSAGE_CONSTRAINTS =
        "Priority should only contain a number between 0 to 10 inclusive, and it should not be blank";

    public final int fullPriority;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority in the form of an index.
     */
    public Priority(int priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        fullPriority = priority;
    }

    /**
     * Constructs a {@code Priority}
     *
     * @param priority a valid priority in the from of a String.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        fullPriority = Integer.parseInt(priority);
    }

    /**
     * Returns true if a given integer is a valid priority.
     */
    public static boolean isValidPriority(int test) {
        return test >= LOWEST_PRIORITY && test <= HIGHEST_PRIORITY;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        try {
            int priority = Integer.parseInt(test);
            return isValidPriority(priority);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(fullPriority);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Priority // instanceof handles nulls
            && fullPriority == ((Priority) other).fullPriority); // state check
    }

    @Override
    public int compareTo(Priority other) {
        return fullPriority - other.fullPriority;
    }

    @Override
    public int hashCode() {
        return String.valueOf(fullPriority).hashCode();
    }
}
