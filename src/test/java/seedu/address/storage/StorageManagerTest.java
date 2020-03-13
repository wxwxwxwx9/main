package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.InternshipDiary;
import seedu.address.model.ReadOnlyInternshipDiary;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonInternshipDiaryStorage internshipDiaryStorage = new JsonInternshipDiaryStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(internshipDiaryStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void internshipDiaryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInternshipDiaryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonInternshipDiaryStorageTest} class.
         */
        InternshipDiary original = getTypicalInternshipDiary();
        storageManager.saveInternshipDiary(original);
        ReadOnlyInternshipDiary retrieved = storageManager.readInternshipDiary().get();
        InternshipDiary retrievedDiary = new InternshipDiary(retrieved);
        boolean isSame = original.equals(retrievedDiary);
        assertEquals(original, new InternshipDiary(retrieved));
    }

    @Test
    public void getInternshipDiaryFilePath() {
        assertNotNull(storageManager.getInternshipDiaryFilePath());
    }

}
