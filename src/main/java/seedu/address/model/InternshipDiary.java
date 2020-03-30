package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.archival.InternshipApplicationViewType;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.UniqueInternshipApplicationList;

/**
 * Wraps all data at the internship-diary level
 * Duplicates are not allowed (by .isSameInternshipApplication comparison)
 */
public class InternshipDiary implements ReadOnlyInternshipDiary {

    private UniqueInternshipApplicationList unarchivedInternships;
    private UniqueInternshipApplicationList archivedInternships;

    /**
     * The internship list that is shown to the user on the interface currently.
     */
    private UniqueInternshipApplicationList displayedInternships;
    private InternshipApplicationViewType currentView = InternshipApplicationViewType.UNARCHIVED;

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        archivedInternships = new UniqueInternshipApplicationList();
        unarchivedInternships = new UniqueInternshipApplicationList();
        // default view is unarchivedInternships
        displayedInternships = unarchivedInternships;
    }

    public InternshipDiary() {}

    /**
     * Creates an InternshipDiary using the InternshipApplications in the {@code toBeCopied}
     */
    public InternshipDiary(ReadOnlyInternshipDiary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// internship list views

    /**
     * Adds a listener for any changes in InternshipDiary.
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     * Sets the displayed internship application(s) list with archived internship application(s) list.
     * Updates its observers about the change in the displayed internships.
     */
    public void viewArchivedInternshipApplicationList() {
        this.displayedInternships = archivedInternships;
        this.currentView = InternshipApplicationViewType.ARCHIVED;
        changes.firePropertyChange("displayedInternships", null, getDisplayedInternshipList());
    }

    /**
     * Sets the displayed internship application(s) list with unarchived internship application(s) list.
     * Updates its observers about the change in the displayed internships.
     */
    public void viewUnarchivedInternshipApplicationList() {
        this.displayedInternships = unarchivedInternships;
        this.currentView = InternshipApplicationViewType.UNARCHIVED;
        changes.firePropertyChange("displayedInternships", null, getDisplayedInternshipList());
    }

    /**
     * Retrieves the current view of the internship applications (either archived or unarchived).
     */
    public InternshipApplicationViewType getCurrentView() {
        return this.currentView;
    }

    //// list overwrite operations

    /**
     * Resets the existing archived and unarchived internship application data of this {@code InternshipDiary}
     * with appropriate applications from {@code newData}.
     */
    public void resetData(ReadOnlyInternshipDiary newData) {
        requireNonNull(newData);
        // temporary implementation to check if newData contains any duplicate internship applications as a whole
        new UniqueInternshipApplicationList().setInternshipApplications(newData.getAllInternshipList());
        setArchivedInternships(newData.getAllInternshipList());
        setUnarchivedInternships(newData.getAllInternshipList());
        viewUnarchivedInternshipApplicationList();
    }

    /**
     * Replaces the contents of the archived internship application list with {@code internshipApplications}.
     * {@code internshipApplications} must not contain duplicate internship applications.
     */
    public void setArchivedInternships(List<InternshipApplication> internshipApplications) {
        List<InternshipApplication> archived = internshipApplications.stream()
                .filter((internshipApplication) -> internshipApplication.isArchived())
                .collect(Collectors.toList());
        this.archivedInternships.setInternshipApplications(archived);
    }

    /**
     * Replaces the contents of the unarchived internship application list with {@code internshipApplications}.
     * {@code internshipApplications} must not contain duplicate internship applications.
     */
    public void setUnarchivedInternships(List<InternshipApplication> internshipApplications) {
        List<InternshipApplication> unarchived = internshipApplications.stream()
                .filter((internshipApplication) -> !internshipApplication.isArchived())
                .collect(Collectors.toList());
        this.unarchivedInternships.setInternshipApplications(unarchived);
    }

    //// internship-application-level operations

    /**
     * Returns true if an internship application with the same identity as {@code internshipApplication}
     * exists in the internship diary.
     */
    public boolean hasInternship(InternshipApplication internshipApplication) {
        requireNonNull(internshipApplication);
        return displayedInternships.contains(internshipApplication);
    }

    /**
     * Archives an internship application and moves it to the unarchived list in the internship diary.
     * The internship application must already exist in the internship diary.
     */
    public void archiveInternshipApplication(InternshipApplication internshipApplication) {
        unarchivedInternships.remove(internshipApplication);
        InternshipApplication archived = internshipApplication.archive();
        archivedInternships.add(archived);
    }

    /**
     * Unarchives an internship application and moves it to the unarchived list in the internship diary.
     * The internship application must already exist in the internship diary.
     */
    public void unarchiveInternshipApplication(InternshipApplication internshipApplication) {
        archivedInternships.remove(internshipApplication);
        InternshipApplication unarchived = internshipApplication.unarchive();
        unarchivedInternships.add(unarchived);
    }

    /**
     * Loads an internship application into the internship diary from storage.
     * Internship application is added to the archived internship application list if it is archived.
     * Otherwise, it is added to the unarchived internship application list.
     */
    public void loadInternshipApplication(InternshipApplication internshipApplication) {
        if (internshipApplication.isArchived()) {
            archivedInternships.add(internshipApplication);
        } else {
            unarchivedInternships.add(internshipApplication);
        }
    }

    /**
     * Adds an internship application to the internship diary from the user interface.
     * The internship application must not already exist in the internship diary.
     */
    public void addInternshipApplication(InternshipApplication internshipApplication) {
        if (currentView.equals(InternshipApplicationViewType.ARCHIVED)) {
            internshipApplication = internshipApplication.archive();
        }
        displayedInternships.add(internshipApplication);
    }

    /**
     * Replaces the given internship application {@code target} in the list with {@code editedInternship}.
     * {@code target} must exist in the internship diary.
     * The internship application identity of {@code editedInternship}
     * must not be the same as another existing internship application in the internship diary.
     */
    public void setInternship(InternshipApplication target, InternshipApplication editedInternship) {
        requireNonNull(editedInternship);

        displayedInternships.setInternshipApplication(target, editedInternship);
    }

    /**
     * Removes {@code key} from this {@code InternshipDiary}.
     * {@code key} must exist in the internship diary.
     */
    public void removeInternship(InternshipApplication key) {
        displayedInternships.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return displayedInternships.asUnmodifiableObservableList().size() + " internship application(s)";
        // TODO: refine later
    }

    @Override
    public ObservableList<InternshipApplication> getDisplayedInternshipList() {
        return displayedInternships.asUnmodifiableObservableList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ObservableList<InternshipApplication> getAllInternshipList() {
        return FXCollections.concat(archivedInternships.asUnmodifiableObservableList(),
                unarchivedInternships.asUnmodifiableObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipDiary // instanceof handles nulls
                && archivedInternships.equals(((InternshipDiary) other).archivedInternships))
                && unarchivedInternships.equals(((InternshipDiary) other).unarchivedInternships)
                && currentView.equals(((InternshipDiary) other).currentView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(archivedInternships, unarchivedInternships, currentView);
    }
}
