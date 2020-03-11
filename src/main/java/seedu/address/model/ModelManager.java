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
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the internship diary data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private InternshipDiary internshipDiary = new InternshipDiary();
    private final UserPrefs userPrefs;
    private FilteredList<InternshipApplication> filteredInternshipApplications =
            new FilteredList<>(internshipDiary.getInternshipList());
    //Old AB code
    private AddressBook addressBook = new AddressBook();
    private FilteredList<Person> filteredPersons = new FilteredList<>(addressBook.getPersonList());

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

    // Old AB3 Constructor
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
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

    //============== Old Code ================================================================================

    //=========== UserPrefs ==================================================================================

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

}
