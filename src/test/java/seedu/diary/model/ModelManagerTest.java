package seedu.diary.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.model.ListenerPropertyType.COMPARATOR;
import static seedu.diary.model.ListenerPropertyType.DISPLAYED_INTERNSHIP_DETAIL;
import static seedu.diary.model.ListenerPropertyType.FILTERED_INTERNSHIP_APPLICATIONS;
import static seedu.diary.model.ListenerPropertyType.PREDICATE;
import static seedu.diary.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalInternshipApplications.FACEBOOK;
import static seedu.diary.testutil.TypicalInternshipApplications.GOOGLE;

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

import seedu.diary.commons.core.GuiSettings;
import seedu.diary.logic.comparator.CompanyComparator;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.diary.testutil.InternshipApplicationBuilder;
import seedu.diary.testutil.InternshipDiaryBuilder;

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
    public void hasInternshipApplication_nullInternshipApplication_throwsNullPointerException() {
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
        assertFalse(newUnarchivedGoogleApplication.isArchived());
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
    public void addDisplayedInternsipDetailPropertyChangeListener_listChanged_listenerCalled() {
        InternshipApplication google = GOOGLE;
        InternshipApplication facebook = FACEBOOK;
        class MockListener implements PropertyChangeListener {
            private InternshipApplication oldInternshipApplication = null;
            private InternshipApplication internshipApplication = null;

            @SuppressWarnings("unchecked")
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                oldInternshipApplication = (InternshipApplication) e.getOldValue();
                internshipApplication = (InternshipApplication) e.getNewValue();
            }
        }

        MockListener mockListener = new MockListener();
        modelManager.addPropertyChangeListener(DISPLAYED_INTERNSHIP_DETAIL, mockListener);
        assertNull(mockListener.internshipApplication);

        modelManager.addInternshipApplication(google);
        assertNull(mockListener.oldInternshipApplication);
        assertSame(google, mockListener.internshipApplication);

        modelManager.setInternshipApplication(google, facebook);
        assertSame(google, mockListener.oldInternshipApplication);
        assertSame(facebook, mockListener.internshipApplication);

        modelManager.deleteInternshipApplication(facebook);
        assertSame(facebook, mockListener.oldInternshipApplication);
        assertNull(mockListener.internshipApplication);

        modelManager.displayInternshipDetail(google);
        assertNull(mockListener.oldInternshipApplication);
        assertSame(google, mockListener.internshipApplication);

        modelManager.viewArchivedInternshipApplicationList();
        assertNull(mockListener.oldInternshipApplication);
        assertNull(mockListener.internshipApplication);
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
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different internshipDiary -> returns false
        assertNotEquals(modelManager, new ModelManager(differentDiary, userPrefs));

        // different filteredList -> returns false
        String[] keywords = GOOGLE.getCompany().fullCompany.split("\\s+");
        modelManager.updateFilteredInternshipApplicationList(
            new CompanyContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(diary, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredInternshipApplicationList(PREDICATE_SHOW_ALL_INTERNSHIPS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInternshipDiaryFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(diary, differentUserPrefs));
    }
}
