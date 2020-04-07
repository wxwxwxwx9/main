package seedu.diary.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalInternshipApplications.AMY;
import static seedu.diary.testutil.TypicalInternshipApplications.BOB;
import static seedu.diary.testutil.TypicalInternshipApplications.GOOGLE;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.diary.commons.exceptions.DataConversionException;
import seedu.diary.model.InternshipDiary;
import seedu.diary.model.ReadOnlyInternshipDiary;

public class JsonInternshipDiaryStorageTest {
    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonInternshipDiaryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readInternshipDiary_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readInternshipDiary(null));
    }

    private java.util.Optional<ReadOnlyInternshipDiary> readInternshipDiary(String filePath) throws Exception {
        return new JsonInternshipDiaryStorage(Paths.get(filePath))
            .readInternshipDiary(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInternshipDiary("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
            readInternshipDiary("notJsonFormatInternshipDiary.json"));
    }

    @Test
    public void readInternshipDiary_invalidInternshipInternshipDiary_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readInternshipDiary("invalidInternshipInternshipDiary.json"));
    }

    @Test
    public void readInternshipDiary_invalidAndValidInternshipInternshipDiary_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readInternshipDiary("invalidAndValidInternshipInternshipDiary.json"));
    }

    @Test
    public void readAndSaveInternshipDiary_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempInternshipDiary.json");
        InternshipDiary original = getTypicalInternshipDiary();
        JsonInternshipDiaryStorage jsonInternshipDiaryStorage = new JsonInternshipDiaryStorage(filePath);

        // Save in new file and read back
        jsonInternshipDiaryStorage.saveInternshipDiary(original, filePath);
        ReadOnlyInternshipDiary readBack = jsonInternshipDiaryStorage.readInternshipDiary(filePath).get();
        assertEquals(original, new InternshipDiary(readBack));

        // Modify data, overwrite exiting file, and read back
        original.loadInternshipApplication(AMY);
        original.removeInternship(GOOGLE);
        jsonInternshipDiaryStorage.saveInternshipDiary(original, filePath);
        readBack = jsonInternshipDiaryStorage.readInternshipDiary(filePath).get();
        assertEquals(original, new InternshipDiary(readBack));

        // Save and read without specifying file path
        original.addInternshipApplication(BOB);
        jsonInternshipDiaryStorage.saveInternshipDiary(original); // file path not specified
        readBack = jsonInternshipDiaryStorage.readInternshipDiary().get(); // file path not specified
        assertEquals(original, new InternshipDiary(readBack));

    }

    @Test
    public void saveInternshipDiary_nullInternshipDiary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInternshipDiary(null, "SomeFile.json"));
    }

    /**
     * Saves {@code internshipDiary} at the specified {@code filePath}.
     */
    private void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary, String filePath) {
        try {
            new JsonInternshipDiaryStorage(Paths.get(filePath))
                .saveInternshipDiary(internshipDiary, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInternshipDiary_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInternshipDiary(new InternshipDiary(), null));
    }
}
