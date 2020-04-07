package seedu.diary.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.diary.commons.exceptions.DataConversionException;
import seedu.diary.model.ReadOnlyInternshipDiary;
import seedu.diary.model.ReadOnlyUserPrefs;
import seedu.diary.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends InternshipDiaryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getInternshipDiaryFilePath();

    @Override
    Optional<ReadOnlyInternshipDiary> readInternshipDiary() throws DataConversionException, IOException;

    @Override
    void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary) throws IOException;

}
