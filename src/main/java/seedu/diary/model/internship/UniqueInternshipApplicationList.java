package seedu.diary.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.diary.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.diary.model.internship.exceptions.DuplicateInternshipApplicationException;
import seedu.diary.model.internship.exceptions.InternshipApplicationNotFoundException;

/**
 * A list of internship applications that enforces uniqueness between its elements and does not allow nulls.
 * A internship application is considered unique by comparing
 * using {@code InternshipApplication#InternshipApplication(InternshipApplication)}.
 * As such, adding and updating of internship applications
 * uses InternshipApplication#InternshipApplication(InternshipApplication) for equality
 * so as to ensure that the internship application being added or updated is
 * unique in terms of identity in the UniqueInternshipApplicationList.
 * However, the removal of a internship application uses InternshipApplication#equals(Object) so
 * as to ensure that the internship application with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see InternshipApplication#isSameInternshipApplication(InternshipApplication)
 */
public class UniqueInternshipApplicationList implements Iterable<InternshipApplication> {

    private final ObservableList<InternshipApplication> internalList = FXCollections.observableArrayList();
    private final ObservableList<InternshipApplication> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent internship application as the given argument.
     */
    public boolean contains(InternshipApplication toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameInternshipApplication);
    }

    /**
     * Adds a internship application to the list.
     * The internship application must not already exist in the list.
     */
    public void add(InternshipApplication toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInternshipApplicationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the internship application {@code target} in the list with {@code editedInternshipApplication}.
     * {@code target} must exist in the list.
     * The internship application identity of {@code editedInternshipApplication}
     * must not be the same as another existing internship application in the list.
     */
    public void setInternshipApplication(InternshipApplication target,
        InternshipApplication editedInternshipApplication) {
        requireAllNonNull(target, editedInternshipApplication);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InternshipApplicationNotFoundException();
        }

        if (!target.isSameInternshipApplication(editedInternshipApplication) && contains(editedInternshipApplication)) {
            throw new DuplicateInternshipApplicationException();
        }

        internalList.set(index, editedInternshipApplication);
    }

    /**
     * Removes the equivalent internship application from the list.
     * The internship application must exist in the list.
     */
    public void remove(InternshipApplication toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new InternshipApplicationNotFoundException();
        }
    }

    public void setInternshipApplications(UniqueInternshipApplicationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code internship applications}.
     * {@code internship applications} must not contain duplicate internship applications.
     */
    public void setInternshipApplications(List<InternshipApplication> internshipApplications) {
        requireAllNonNull(internshipApplications);
        if (!internshipApplicationsAreUnique(internshipApplications)) {
            throw new DuplicateInternshipApplicationException();
        }

        internalList.setAll(internshipApplications);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<InternshipApplication> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<InternshipApplication> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueInternshipApplicationList // instanceof handles nulls
            && internalList.equals(((UniqueInternshipApplicationList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code internship applications} contains only unique internship applications.
     */
    private boolean internshipApplicationsAreUnique(List<InternshipApplication> internshipApplications) {
        for (int i = 0; i < internshipApplications.size() - 1; i++) {
            for (int j = i + 1; j < internshipApplications.size(); j++) {
                if (internshipApplications.get(i).isSameInternshipApplication(internshipApplications.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
