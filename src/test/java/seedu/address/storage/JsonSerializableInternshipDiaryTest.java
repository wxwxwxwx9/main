package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.InternshipDiary;
import seedu.address.testutil.TypicalInternshipApplications;

public class JsonSerializableInternshipDiaryTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableInternshipDiaryTest");
    private static final Path TYPICAL_INTERNSHIPS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsInternshipDiary.json");
    private static final Path INVALID_INTERNSHIP_FILE = TEST_DATA_FOLDER.resolve("invalidPersonInternshipDiary.json");
    private static final Path DUPLICATE_INTERNSHIP_FILE =
            TEST_DATA_FOLDER.resolve("duplicatePersonInternshipDiary.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableInternshipDiary dataFromFile = JsonUtil.readJsonFile(TYPICAL_INTERNSHIPS_FILE,
                JsonSerializableInternshipDiary.class).get();
        InternshipDiary addressBookFromFile = dataFromFile.toModelType();
        InternshipDiary typicalPersonsInternshipDiary = TypicalInternshipApplications.getTypicalInternshipDiary();
        assertEquals(addressBookFromFile, typicalPersonsInternshipDiary);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInternshipDiary dataFromFile = JsonUtil.readJsonFile(INVALID_INTERNSHIP_FILE,
                JsonSerializableInternshipDiary.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableInternshipDiary dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INTERNSHIP_FILE,
                JsonSerializableInternshipDiary.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableInternshipDiary.MESSAGE_DUPLICATE_INTERNSHIP,
                dataFromFile::toModelType);
    }

}
