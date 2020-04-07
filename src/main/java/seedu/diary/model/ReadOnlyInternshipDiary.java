package seedu.diary.model;

import java.beans.PropertyChangeListener;

import javafx.collections.ObservableList;

import seedu.diary.model.internship.InternshipApplication;


/**
 * Unmodifiable view of an internship diary
 */
public interface ReadOnlyInternshipDiary {

    /**
     * Returns an unmodifiable view of the currently displayed internship application list.
     * This list will not contain any duplicate internship application.
     */
    ObservableList<InternshipApplication> getDisplayedInternshipList();

    /**
     * Returns an unmodifiable view of both the archived and unarchived internship application list.
     * This list will not contain any duplicate internship application.
     */
    ObservableList<InternshipApplication> getAllInternshipList();

    /**
     * Adds a property listener for any changes in {@code propertyType} used.
     */
    void addPropertyChangeListener(ListenerPropertyType propertyType, PropertyChangeListener l);

}
