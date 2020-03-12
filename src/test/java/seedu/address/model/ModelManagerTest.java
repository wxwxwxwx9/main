package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;
import static seedu.address.testutil.Assert.assertThrows;

import static seedu.address.testutil.TypicalInternshipApplications.FACEBOOK;
import static seedu.address.testutil.TypicalInternshipApplications.GOOGLE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;
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
