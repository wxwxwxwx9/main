package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.internship.InternshipApplication;

/**
 * Unmodifiable view of an internship diary
 */
public interface ReadOnlyInternshipDiary {

    /**
     * Returns an unmodifiable view of the internship application list.
     * This list will not contain any duplicate internship application.
     */
    ObservableList<InternshipApplication> getInternshipList();

}
