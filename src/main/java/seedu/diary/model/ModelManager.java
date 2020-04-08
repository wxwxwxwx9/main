package seedu.diary.model;

import static java.util.Objects.requireNonNull;
import static seedu.diary.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.diary.model.ListenerPropertyType.COMPARATOR;
import static seedu.diary.model.ListenerPropertyType.DISPLAYED_INTERNSHIPS;
import static seedu.diary.model.ListenerPropertyType.DISPLAYED_INTERNSHIP_DETAIL;
import static seedu.diary.model.ListenerPropertyType.FILTERED_INTERNSHIP_APPLICATIONS;
import static seedu.diary.model.ListenerPropertyType.PREDICATE;
import static seedu.diary.model.ListenerPropertyType.VIEW_TYPE;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import seedu.diary.commons.core.GuiSettings;
import seedu.diary.commons.core.LogsCenter;
import seedu.diary.commons.core.archival.InternshipApplicationViewType;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.statistics.Statistics;

/**
 * Represents the in-memory model of the internship diary data.
 */
public class ModelManager implements Model, PropertyChangeListener {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final Statistics statistics;

    private InternshipDiary internshipDiary;

    private FilteredList<InternshipApplication> filteredInternshipApplications;
    private SortedList<InternshipApplication> sortedFilteredInternshipApplications;

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * Initializes a ModelManager with the given internshipDiary and userPrefs.
     */
    public ModelManager(ReadOnlyInternshipDiary internshipDiary, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(internshipDiary, userPrefs);

        logger.fine("Initializing with internship diary: " + internshipDiary + " and user prefs " + userPrefs);

        this.internshipDiary = new InternshipDiary(internshipDiary);
        // Model manager listens to any changes in displayedInternships in internshipDiary
        this.internshipDiary.addPropertyChangeListener(DISPLAYED_INTERNSHIPS, this);
        this.userPrefs = new UserPrefs(userPrefs);
        this.statistics = new Statistics();
        filteredInternshipApplications = new FilteredList<>(this.internshipDiary.getDisplayedInternshipList());
        sortedFilteredInternshipApplications = new SortedList<>(filteredInternshipApplications);
    }

    public ModelManager() {
        this(new InternshipDiary(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getInternshipDiaryFilePath() {
        return userPrefs.getInternshipDiaryFilePath();
    }

    @Override
    public void setInternshipDiaryFilePath(Path internshipDiaryFilePath) {
        requireNonNull(internshipDiaryFilePath);
        userPrefs.setInternshipDiaryFilePath(internshipDiaryFilePath);
    }

    //=========== InternshipDiary ============================================================================

    @Override
    public ReadOnlyInternshipDiary getInternshipDiary() {
        return internshipDiary;
    }

    @Override
    public void setInternshipDiary(ReadOnlyInternshipDiary internshipDiary) {
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, null);
        this.internshipDiary.resetData(internshipDiary);
    }

    @Override
    public void addInternshipApplication(InternshipApplication internshipApplication) {
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, internshipApplication);
        internshipDiary.addInternshipApplication(internshipApplication);
    }

    @Override
    public void deleteInternshipApplication(InternshipApplication target) {
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, target, null);
        internshipDiary.removeInternship(target);
    }

    @Override
    public void archiveInternshipApplication(InternshipApplication target) {
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, target, null);
        internshipDiary.archiveInternshipApplication(target);
    }

    @Override
    public void unarchiveInternshipApplication(InternshipApplication target) {
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, target, null);
        internshipDiary.unarchiveInternshipApplication(target);
    }

    @Override
    public boolean hasInternshipApplication(InternshipApplication internshipApplication) {
        requireNonNull(internshipApplication);
        return internshipDiary.hasInternship(internshipApplication);
    }

    @Override
    public void setInternshipApplication(InternshipApplication target, InternshipApplication editedInternship) {
        requireAllNonNull(target, editedInternship);
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, target, editedInternship);
        internshipDiary.setInternship(target, editedInternship);
    }

    //=========== Internship Application List Accessors =============================================

    /**
     * Returns an unmodifiable view of the concatenated archived and unarchived list of {@code InternshipApplication}
     * backed by the internal list of {@code versionedInternshipDiary}
     */
    @Override
    public ObservableList<InternshipApplication> getAllInternshipApplicationList() {
        return internshipDiary.getAllInternshipList();
    }

    /**
     * Returns an unmodifiable view of the current list of {@code InternshipApplication}
     * backed by the internal list of {@code versionedInternshipDiary}
     */
    @Override
    public ObservableList<InternshipApplication> getFilteredInternshipApplicationList() {
        return sortedFilteredInternshipApplications;
    }

    @Override
    public void updateFilteredInternshipApplicationList(Predicate<InternshipApplication> predicate) {
        requireNonNull(predicate);
        filteredInternshipApplications.setPredicate(predicate);
        firePropertyChange(PREDICATE, predicate);
    }

    @Override
    public void updateFilteredInternshipApplicationList(Comparator<InternshipApplication> comparator) {
        requireNonNull(comparator);
        sortedFilteredInternshipApplications.setComparator(comparator);
        firePropertyChange(COMPARATOR, comparator);
    }

    //=========== Archival view ==================================================================================

    @Override
    public void viewArchivedInternshipApplicationList() {
        internshipDiary.viewArchivedInternshipApplicationList();
    }

    @Override
    public void viewUnarchivedInternshipApplicationList() {
        internshipDiary.viewUnarchivedInternshipApplicationList();
    }

    @Override
    public InternshipApplicationViewType getCurrentView() {
        return internshipDiary.getCurrentView();
    }

    //=========== Statistics ==================================================================================

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    //=========== PropertyChanges ======================================================================

    @Override
    public void displayInternshipDetail(InternshipApplication internshipApplication) {
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, internshipApplication);
    }

    @Override
    public void addPropertyChangeListener(ListenerPropertyType propertyType, PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyType.toString(), l);
    }

    private void firePropertyChange(ListenerPropertyType propertyType, Object newValue) {
        changes.firePropertyChange(propertyType.toString(), null, newValue);
    }

    private void firePropertyChange(ListenerPropertyType propertyType, Object oldValue, Object newValue) {
        changes.firePropertyChange(propertyType.toString(), oldValue, newValue);
    }

    /**
     * Receives the latest changes in displayed internships from internship diary.
     * Updates the filtered and sorted internship applications accordingly
     * and fires property change event to its listeners.
     *
     * @param e event that describes the changes in the updated property.
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        refreshFilteredInternshipApplications(e.getNewValue());
        fireAllPropertyChanges();
    }

    /**
     * Updates the current filtered internship applicaations with the refreshed displayed internship applications.
     *
     * @param newInternshipApplications the new list to replace the current filtered internship applications.
     */
    @SuppressWarnings("unchecked")
    private void refreshFilteredInternshipApplications(Object newInternshipApplications) {
        ObservableList<InternshipApplication> ia = (ObservableList<InternshipApplication>) newInternshipApplications;
        filteredInternshipApplications = new FilteredList<>(ia);
        sortedFilteredInternshipApplications = new SortedList<>(filteredInternshipApplications);
    }

    /**
     * Fires all the relevant property changes to the listeners.
     */
    private void fireAllPropertyChanges() {
        firePropertyChange(FILTERED_INTERNSHIP_APPLICATIONS, getFilteredInternshipApplicationList());
        firePropertyChange(DISPLAYED_INTERNSHIP_DETAIL, null);
        firePropertyChange(COMPARATOR, null);
        firePropertyChange(PREDICATE, null);
        firePropertyChange(VIEW_TYPE, getCurrentView());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return internshipDiary.equals(other.internshipDiary)
            && userPrefs.equals(other.userPrefs)
            && filteredInternshipApplications.equals(other.filteredInternshipApplications);
    }

}
