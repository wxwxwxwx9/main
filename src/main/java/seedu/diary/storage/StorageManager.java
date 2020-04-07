package seedu.diary.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.diary.commons.core.LogsCenter;
import seedu.diary.commons.exceptions.DataConversionException;
import seedu.diary.model.ReadOnlyInternshipDiary;
import seedu.diary.model.ReadOnlyUserPrefs;
import seedu.diary.model.UserPrefs;

/**
 * Manages storage of InternshipDiary data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private InternshipDiaryStorage internshipDiaryStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(InternshipDiaryStorage internshipDiaryStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.internshipDiaryStorage = internshipDiaryStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ InternshipDiary methods ==============================

    @Override
    public Path getInternshipDiaryFilePath() {
        return internshipDiaryStorage.getInternshipDiaryFilePath();
    }

    @Override
    public Optional<ReadOnlyInternshipDiary> readInternshipDiary() throws DataConversionException, IOException {
        return readInternshipDiary(internshipDiaryStorage.getInternshipDiaryFilePath());
    }

    @Override
    public Optional<ReadOnlyInternshipDiary> readInternshipDiary(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return internshipDiaryStorage.readInternshipDiary(filePath);
    }

    @Override
    public void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary) throws IOException {
        saveInternshipDiary(internshipDiary, internshipDiaryStorage.getInternshipDiaryFilePath());
    }

    @Override
    public void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        internshipDiaryStorage.saveInternshipDiary(internshipDiary, filePath);
    }

}
