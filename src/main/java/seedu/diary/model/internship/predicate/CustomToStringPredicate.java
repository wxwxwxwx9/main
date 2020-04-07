package seedu.diary.model.internship.predicate;

import java.util.function.Predicate;

/**
 * Gives a predicate an overwritten toString method.
 */
public class CustomToStringPredicate<T> implements Predicate<T> {
    private final Predicate<T> internalPredicate;
    private final String internalString;

    public CustomToStringPredicate(Predicate<T> internalPredicate, String internalString) {
        this.internalPredicate = internalPredicate;
        this.internalString = internalString;
    }

    @Override
    public boolean test(T t) {
        return internalPredicate.test(t);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof CustomToStringPredicate // instanceof handles nulls
            && internalPredicate.equals(((CustomToStringPredicate<?>) other).internalPredicate)
            && internalString.equals(((CustomToStringPredicate<?>) other).internalString);
    }

    @Override
    public String toString() {
        return internalString;
    }
}
