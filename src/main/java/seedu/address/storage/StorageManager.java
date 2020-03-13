package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyInternshipDiary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of InternshipDiary data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private InternshipDiaryStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(InternshipDiaryStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
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
        return addressBookStorage.getInternshipDiaryFilePath();
    }

    @Override
    public Optional<ReadOnlyInternshipDiary> readInternshipDiary() throws DataConversionException, IOException {
        return readInternshipDiary(addressBookStorage.getInternshipDiaryFilePath());
    }

    @Override
    public Optional<ReadOnlyInternshipDiary> readInternshipDiary(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readInternshipDiary(filePath);
    }

    @Override
    public void saveInternshipDiary(ReadOnlyInternshipDiary addressBook) throws IOException {
        saveInternshipDiary(addressBook, addressBookStorage.getInternshipDiaryFilePath());
    }

    @Override
    public void saveInternshipDiary(ReadOnlyInternshipDiary addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveInternshipDiary(addressBook, filePath);
    }

}
