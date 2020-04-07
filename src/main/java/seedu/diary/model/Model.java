package seedu.diary.model;

import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.diary.commons.core.GuiSettings;
import seedu.diary.commons.core.archival.InternshipApplicationViewType;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.statistics.Statistics;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<InternshipApplication> PREDICATE_SHOW_ALL_INTERNSHIPS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' internship diary file path.
     */
    Path getInternshipDiaryFilePath();

    /**
     * Sets the user prefs' internship diary file path.
     *
     * @param internshipDiaryFilePath new file path.
     */
    void setInternshipDiaryFilePath(Path internshipDiaryFilePath);

    /**
     * Replaces internship diary with the data in {@code internshipDiary}
     *
     * @param internshipDiary new internship diary.
     */
    void setInternshipDiary(ReadOnlyInternshipDiary internshipDiary);

    /**
     * Returns the InternshipDiary
     */
    ReadOnlyInternshipDiary getInternshipDiary();

    /**
     * Returns true if an internship application with the same identity as {@code internshipApplication}
     * exists in the Internship Diary.
     */
    boolean hasInternshipApplication(InternshipApplication internshipApplication);

    /**
     * Archives the given internship application.
     * The application must exist in the internship diary.
     */
    void archiveInternshipApplication(InternshipApplication target);

    /**
     * Unarchives the given internship application.
     * The application must exist in the internship diary.
     */
    void unarchiveInternshipApplication(InternshipApplication target);

    /**
     * Deletes the given internship application.
     * The application must exist in the internship diary.
     */
    void deleteInternshipApplication(InternshipApplication target);

    /**
     * Adds the given internship application.
     * {@code internshipApplication} must not already exist in the internship diary
     */
    void addInternshipApplication(InternshipApplication internshipApplication);

    /**
     * Replaces the given internship application {@code target} with {@code editedInternship}.
     * {@code target} must exist in the internship application.
     * The internship application identify of {@code editedInternship} must not be the same
     * as another existing internship application in the internship diary.
     */
    void setInternshipApplication(InternshipApplication target, InternshipApplication editedInternship);

    /**
     * Returns an unmodifiable view of the filtered internship applications list
     */
    ObservableList<InternshipApplication> getFilteredInternshipApplicationList();

    /**
     * Updates the filter of the filtered internship application list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInternshipApplicationList(Predicate<InternshipApplication> predicate);

    /**
     * Updates the filter of the filtered internship application list to sort by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInternshipApplicationList(Comparator<InternshipApplication> comparator);

    /**
     * Updates the currently used internship application(s) list with the archived internship application list(s).
     */
    void viewArchivedInternshipApplicationList();

    /**
     * Updates the currently used internship application(s) list with the unarchived internship application list(s).
     */
    void viewUnarchivedInternshipApplicationList();

    /**
     * Returns an unmodifiable view of the concatenated archived and unarchived internship applications list.
     */
    ObservableList<InternshipApplication> getAllInternshipApplicationList();

    /**
     * Retrieves the current view of the internship diary.
     */
    InternshipApplicationViewType getCurrentView();

    /**
     * Changes the currently displayed InternshipApplicationDetail.
     */
    void displayInternshipDetail(InternshipApplication internshipApplication);

    /**
     * Adds a property listener for any changes in {@code propertyType} used.
     */
    void addPropertyChangeListener(ListenerPropertyType propertyType, PropertyChangeListener l);

    /**
     * Returns a statistics object that can compute relevant internship application statistics.
     */
    Statistics getStatistics();

}
