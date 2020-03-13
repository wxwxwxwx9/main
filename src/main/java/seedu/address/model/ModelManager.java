package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.internship.InternshipApplication;

/**
 * Represents the in-memory model of the internship diary data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;

    private InternshipDiary internshipDiary = new InternshipDiary();
    private FilteredList<InternshipApplication> filteredInternshipApplications =
            new FilteredList<>(internshipDiary.getInternshipList());

    /**
     * Initializes a ModelManager with the given internshipDiary and userPrefs.
     */
    public ModelManager(ReadOnlyInternshipDiary internshipDiary, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(internshipDiary, userPrefs);

        logger.fine("Initializing with internship diary: " + internshipDiary + " and user prefs " + userPrefs);

        this.internshipDiary = new InternshipDiary(internshipDiary);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredInternshipApplications = new FilteredList<>(this.internshipDiary.getInternshipList());
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
    public void setInternshipDiary(ReadOnlyInternshipDiary internshipDiary) {
        this.internshipDiary.resetData(internshipDiary);
    }

    @Override
    public ReadOnlyInternshipDiary getInternshipDiary() {
        return internshipDiary;
    }

    @Override
    public boolean hasInternshipApplication(InternshipApplication internshipApplication) {
        requireNonNull(internshipApplication);
        return internshipDiary.hasInternship(internshipApplication);
    }

    @Override
    public void deleteInternshipApplication(InternshipApplication target) {
        internshipDiary.removeInternship(target);
    }

    @Override
    public void addInternshipApplication(InternshipApplication internshipApplication) {
        internshipDiary.addInternshipApplication(internshipApplication);
    }

    @Override
    public void setInternshipApplication(InternshipApplication target, InternshipApplication editedInternship) {
        requireAllNonNull(target, editedInternship);

        internshipDiary.setInternship(target, editedInternship);
    }

    //=========== Filtered Internship Application List Accessors =============================================

    /**
     * Returns an unmodifiable view of the list of {@code InternshipApplication}
     * backed by the internal list of {@code versionedInternshipDiary}
     */
    @Override
    public ObservableList<InternshipApplication> getFilteredInternshipApplicationList() {
        return filteredInternshipApplications;
    }

    @Override
    public void updateFilteredInternshipApplicationList(Predicate<InternshipApplication> predicate) {
        requireNonNull(predicate);

        filteredInternshipApplications.setPredicate(predicate);
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
