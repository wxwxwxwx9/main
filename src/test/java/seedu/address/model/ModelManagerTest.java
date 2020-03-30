package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ListenerPropertyType.COMPARATOR;
import static seedu.address.model.ListenerPropertyType.FILTERED_INTERNSHIP_APPLICATIONS;
import static seedu.address.model.ListenerPropertyType.PREDICATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternshipApplications.FACEBOOK;
import static seedu.address.testutil.TypicalInternshipApplications.GOOGLE;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.comparator.CompanyComparator;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.InternshipDiaryBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new InternshipDiary(), new InternshipDiary(modelManager.getInternshipDiary()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setInternshipDiaryFilePath(Paths.get("internship-diary/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setInternshipDiaryFilePath(Paths.get("internship-diary/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setInternshipDiaryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setInternshipDiaryFilePath(null));
    }

    @Test
    public void setInternshipDiaryFilePath_validPath_setsInternshipDiaryFilePath() {
        Path path = Paths.get("internship-diary/file/path");
        modelManager.setInternshipDiaryFilePath(path);
        assertEquals(path, modelManager.getInternshipDiaryFilePath());
    }

    @Test
    public void hasInternshipApplication_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasInternshipApplication(null));
    }

    @Test
    public void hasInternshipApplication_internshipApplicationNotInInternshipDiary_returnsFalse() {
        assertFalse(modelManager.hasInternshipApplication(GOOGLE));
    }

    @Test
    public void hasInternshipApplication_internshipApplicationInInternshipDiary_returnsTrue() {
        modelManager.addInternshipApplication(GOOGLE);
        assertTrue(modelManager.hasInternshipApplication(GOOGLE));
    }

    @Test
    public void archiveInternshipApplication_internshipApplicationIsUnarchived_success() {
        modelManager.addInternshipApplication(GOOGLE);
        modelManager.archiveInternshipApplication(GOOGLE);
        modelManager.viewArchivedInternshipApplicationList();
        InternshipApplication newArchivedGoogleApplication =
            modelManager.getInternshipDiary().getDisplayedInternshipList().get(0);

        assertTrue(newArchivedGoogleApplication.isArchived());
    }

    @Test
    public void unarchiveInternshipApplication_internshipApplicationIsArchived_success() {
        modelManager.addInternshipApplication(GOOGLE);
        modelManager.archiveInternshipApplication(GOOGLE);
        modelManager.viewArchivedInternshipApplicationList();
        InternshipApplication newArchivedGoogleApplication =
            modelManager.getInternshipDiary().getDisplayedInternshipList().get(0);
        modelManager.unarchiveInternshipApplication(newArchivedGoogleApplication);
        modelManager.viewUnarchivedInternshipApplicationList();
        InternshipApplication newUnarchivedGoogleApplication =
            modelManager.getInternshipDiary().getDisplayedInternshipList().get(0);
        assertTrue(!newUnarchivedGoogleApplication.isArchived());
    }

    @Test
    public void addComparatorPropertyChangeListener_comparatorChanged_listenerCalled() {
        class MockListener implements PropertyChangeListener {
            private Comparator<InternshipApplication> comparator = null;

            @SuppressWarnings("unchecked")
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                comparator = (Comparator<InternshipApplication>) e.getNewValue();
            }
        }

        MockListener mockListener = new MockListener();
        modelManager.addPropertyChangeListener(COMPARATOR, mockListener);
        assertNull(mockListener.comparator);
        Comparator<InternshipApplication> comparator1 = new CompanyComparator();
        modelManager.updateFilteredInternshipApplicationList(comparator1);
        assertSame(comparator1, mockListener.comparator);

        modelManager.viewArchivedInternshipApplicationList();
        assertNull(mockListener.comparator);
    }

    @Test
    public void addPredicatePropertyChangeListener_comparatorChanged_listenerCalled() {
        class MockListener implements PropertyChangeListener {
            private Predicate<InternshipApplication> predicate = null;

            @SuppressWarnings("unchecked")
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                predicate = (Predicate<InternshipApplication>) e.getNewValue();
            }
        }

        MockListener mockListener = new MockListener();
        modelManager.addPropertyChangeListener(PREDICATE, mockListener);
        assertNull(mockListener.predicate);

        Predicate<InternshipApplication> addressPredicate =
            new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        modelManager.updateFilteredInternshipApplicationList(addressPredicate);
        assertSame(addressPredicate, mockListener.predicate);

        modelManager.viewArchivedInternshipApplicationList();
        assertNull(mockListener.predicate);
    }

    @Test
    public void addFilteredInternshipApplicationsPropertyChangeListener_propertyChanged_listenerCalled() {
        // create mock listener
        class MockListener implements PropertyChangeListener {
            private ObservableList<InternshipApplication> filteredInternshipApplications = null;

            @SuppressWarnings("unchecked")
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                filteredInternshipApplications = (ObservableList<InternshipApplication>) e.getNewValue();
            }
        }
        MockListener mockListener = new MockListener();
        assertNull(mockListener.filteredInternshipApplications);

        // create mock diary and filtered list
        InternshipDiary mockInternshipDiary = new InternshipDiary();
        mockInternshipDiary.loadInternshipApplication(new InternshipApplicationBuilder().build());
        FilteredList<InternshipApplication> mockInternshipApplicationFilteredList =
            new FilteredList<>(mockInternshipDiary.getDisplayedInternshipList());

        // add listener
        modelManager.addPropertyChangeListener(FILTERED_INTERNSHIP_APPLICATIONS, mockListener);

        // create mock property change event
        PropertyChangeEvent e = new PropertyChangeEvent(
            mockInternshipDiary, FILTERED_INTERNSHIP_APPLICATIONS.toString(), null,
            mockInternshipApplicationFilteredList
        );
        modelManager.propertyChange(e);

        assertSame(modelManager.getFilteredInternshipApplicationList(), mockListener.filteredInternshipApplications);
    }

    @Test
    public void getFilteredInternshipApplicationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            modelManager.getFilteredInternshipApplicationList().remove(0));
    }

    @Test
    public void equals() {
        InternshipDiary diary = new InternshipDiaryBuilder()
            .withInternshipApplication(GOOGLE)
            .withInternshipApplication(FACEBOOK)
            .build();
        InternshipDiary differentDiary = new InternshipDiary();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(diary, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(diary, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDiary, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = GOOGLE.getCompany().fullCompany.split("\\s+");
        modelManager.updateFilteredInternshipApplicationList(
            new CompanyContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(diary, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredInternshipApplicationList(PREDICATE_SHOW_ALL_INTERNSHIPS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInternshipDiaryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(diary, differentUserPrefs)));
    }
}
