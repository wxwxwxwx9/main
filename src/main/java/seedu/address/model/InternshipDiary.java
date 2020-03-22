package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.UniqueInternshipApplicationList;

/**
 * Wraps all data at the internship-diary level
 * Duplicates are not allowed (by .isSameInternshipApplication comparison)
 */
public class InternshipDiary implements ReadOnlyInternshipDiary {

    private final UniqueInternshipApplicationList internships;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        internships = new UniqueInternshipApplicationList();
    }

    public InternshipDiary() {}

    /**
     * Creates an InternshipDiary using the InternshipApplications in the {@code toBeCopied}
     */
    public InternshipDiary(ReadOnlyInternshipDiary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the internship application list with {@code internshipApplications}.
     * {@code internshipApplications} must not contain duplicate internship applications.
     */
    public void setInternships(List<InternshipApplication> internshipApplications) {
        this.internships.setInternshipApplications(internshipApplications);
    }

    /**
     * Resets the existing data of this {@code InternshipDiary} with {@code newData}.
     */
    public void resetData(ReadOnlyInternshipDiary newData) {
        requireNonNull(newData);

        setInternships(newData.getInternshipList());
    }

    //// internship-application-level operations

    /**
     * Returns true if an internship application with the same identity as {@code internshipApplication}
     * exists in the internship diary.
     */
    public boolean hasInternship(InternshipApplication internshipApplication) {
        requireNonNull(internshipApplication);
        return internships.contains(internshipApplication);
    }

    /**
     * Adds an internship application to the internship diary.
     * The internship application must not already exist in the internship diary.
     */
    public void addInternshipApplication(InternshipApplication i) {
        internships.add(i);
    }

    /**
     * Replaces the given internship application {@code target} in the list with {@code editedInternship}.
     * {@code target} must exist in the internship diary.
     * The internship application identity of {@code editedInternship}
     * must not be the same as another existing internship application in the internship diary.
     */
    public void setInternship(InternshipApplication target, InternshipApplication editedInternship) {
        requireNonNull(editedInternship);

        internships.setInternshipApplication(target, editedInternship);
    }

    /**
     * Removes {@code key} from this {@code InternshipDiary}.
     * {@code key} must exist in the internship diary.
     */
    public void removeInternship(InternshipApplication key) {
        internships.remove(key);
    }

//    /**
//     * Archives {@code key} from this {@code InternshipDiary}.
//     * {@code key} must exist in the internship diary.
//     */
//    public void archiveInternship(InternshipApplication key) {
//        internships.remove(key);
//    }

    //// util methods

    @Override
    public String toString() {
        return internships.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<InternshipApplication> getInternshipList() {
        return internships.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipDiary // instanceof handles nulls
                && internships.equals(((InternshipDiary) other).internships));
    }

    @Override
    public int hashCode() {
        return internships.hashCode();
    }
}
