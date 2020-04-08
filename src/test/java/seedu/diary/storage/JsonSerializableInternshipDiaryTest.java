package seedu.diary.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.diary.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.exceptions.IllegalValueException;
import seedu.diary.commons.util.JsonUtil;
import seedu.diary.model.InternshipDiary;
import seedu.diary.testutil.TypicalInternshipApplications;

public class JsonSerializableInternshipDiaryTest {

    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonSerializableInternshipDiaryTest");
    private static final Path TYPICAL_INTERNSHIPS_FILE =
        TEST_DATA_FOLDER.resolve("typicalInternshipsInternshipDiary.json");
    private static final Path INVALID_INTERNSHIP_FILE =
        TEST_DATA_FOLDER.resolve("invalidInternshipInternshipDiary.json");
    private static final Path DUPLICATE_INTERNSHIP_FILE =
        TEST_DATA_FOLDER.resolve("duplicateInternshipInternshipDiary.json");


    @Test
    public void toModelType_typicalInternshipsFile_success() throws Exception {
        JsonSerializableInternshipDiary dataFromFile = JsonUtil.readJsonFile(TYPICAL_INTERNSHIPS_FILE,
            JsonSerializableInternshipDiary.class).get();
        InternshipDiary internshipDiaryFromFile = dataFromFile.toModelType();
        InternshipDiary typicalInternshipsInternshipDiary = TypicalInternshipApplications.getTypicalInternshipDiary();
        assertEquals(internshipDiaryFromFile, typicalInternshipsInternshipDiary);
    }

    @Test
    public void toModelType_invalidInternshipFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInternshipDiary dataFromFile = JsonUtil.readJsonFile(INVALID_INTERNSHIP_FILE,
            JsonSerializableInternshipDiary.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateInternships_throwsIllegalValueException() throws Exception {
        JsonSerializableInternshipDiary dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INTERNSHIP_FILE,
            JsonSerializableInternshipDiary.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableInternshipDiary.MESSAGE_DUPLICATE_INTERNSHIP,
            dataFromFile::toModelType);
    }

}
