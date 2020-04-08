package seedu.diary.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.diary.commons.exceptions.DataConversionException;
import seedu.diary.model.ReadOnlyInternshipDiary;

/**
 * Represents a storage for {@link seedu.diary.model.InternshipDiary}.
 */
public interface InternshipDiaryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getInternshipDiaryFilePath();

    /**
     * Returns InternshipDiary data as a {@link ReadOnlyInternshipDiary}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyInternshipDiary> readInternshipDiary() throws DataConversionException, IOException;

    /**
     * @see #getInternshipDiaryFilePath()
     */
    Optional<ReadOnlyInternshipDiary> readInternshipDiary(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyInternshipDiary} to the storage.
     *
     * @param internshipDiary cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary) throws IOException;

    /**
     * @see #saveInternshipDiary(ReadOnlyInternshipDiary)
     */
    void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary, Path filePath) throws IOException;

}
