package seedu.diary.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.diary.commons.core.LogsCenter;
import seedu.diary.commons.exceptions.DataConversionException;
import seedu.diary.commons.exceptions.IllegalValueException;
import seedu.diary.commons.util.FileUtil;
import seedu.diary.commons.util.JsonUtil;
import seedu.diary.model.ReadOnlyInternshipDiary;

/**
 * A class to access InternshipDiary data stored as a json file on the hard disk.
 */
public class JsonInternshipDiaryStorage implements InternshipDiaryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInternshipDiaryStorage.class);

    private Path filePath;

    public JsonInternshipDiaryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInternshipDiaryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInternshipDiary> readInternshipDiary() throws DataConversionException {
        return readInternshipDiary(filePath);
    }

    /**
     * Similar to {@link #readInternshipDiary()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyInternshipDiary> readInternshipDiary(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableInternshipDiary> jsonInternshipDiary = JsonUtil.readJsonFile(
            filePath, JsonSerializableInternshipDiary.class);
        if (jsonInternshipDiary.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInternshipDiary.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary) throws IOException {
        saveInternshipDiary(internshipDiary, filePath);
    }

    /**
     * Similar to {@link #saveInternshipDiary(ReadOnlyInternshipDiary)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary, Path filePath) throws IOException {
        requireNonNull(internshipDiary);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInternshipDiary(internshipDiary), filePath);
    }

}
