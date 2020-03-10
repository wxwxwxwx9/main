package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given internship application {@code target} with {@code editedApplication}.
     * {@code target} must exist in the internship application.
     * The internship application identify of {@code editedApplication} must not be the same
     * as another existing internship application in the internship diary.
     */
    void setInternshipApplication(InternshipApplication target, InternshipApplication editedApplication);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns the user prefs' internship diary file path.
     */
    Path getInternshipDiaryFilePath();

    /**
     * Sets the user prefs' address book file path.
     * @param internshipDiaryFilePath new file path.
     */
    void setInternshipDiaryFilePath(Path internshipDiaryFilePath);

    /**
     * Replaces internship diary with the data in {@code internshipDiary}
     * @param internshipDiary new internship diary.
     */
    void setInternshipDiary(ReadOnlyInternshipDiary internshipDiary);

    /** Returns the InternshipDiary*/
    ReadOnlyInternshipDiary getInternshipDiary();

    /**
     * Returns true if an internship application with the same identity as {@code internshipApplication}
     * exists in the Internship Diary.
     */
    boolean hasInternshipApplication(InternshipApplication internshipApplication);

    /**
     * Deletes the given internship application.
     * The application must exist in the internship diary.
     */
    void deleteInternshipApplication(InternshipApplication internshipApplication);

    /**
     * Adds the given internship application.
     * {@code internshipApplication} must not already exist in the internship diary
     */
    void addInternshipApplication(InternshipApplication internshipApplication);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<InternshipApplication> getFilteredInternshipApplicationList();

    /**
     * Updates the filter of the filtered internship application list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInternshipApplicationList(Predicate<InternshipApplication> predicate);
}
