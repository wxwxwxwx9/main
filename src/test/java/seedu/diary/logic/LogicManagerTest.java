package seedu.diary.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX;
import static seedu.diary.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.diary.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.PRIORITY_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalInternshipApplications.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.diary.logic.commands.AddCommand;
import seedu.diary.logic.commands.CommandResult;
import seedu.diary.logic.commands.InitClearCommand;
import seedu.diary.logic.commands.ListCommand;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.logic.parser.ClearCommandConfirmationParser;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.ReadOnlyInternshipDiary;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.storage.JsonInternshipDiaryStorage;
import seedu.diary.storage.JsonUserPrefsStorage;
import seedu.diary.storage.StorageManager;
import seedu.diary.testutil.InternshipApplicationBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonInternshipDiaryStorage internshipDiaryStorage =
            new JsonInternshipDiaryStorage(temporaryFolder.resolve("internshipDiary.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(internshipDiaryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_commandChangeParser_parserChanged() throws Exception {
        String initClearCommand = InitClearCommand.COMMAND_WORD;
        assertCommandSuccess(initClearCommand, InitClearCommand.MESSAGE_SUCCESS, model);

        String listCommand = ListCommand.COMMAND_WORD;
        assertParseException(listCommand, ClearCommandConfirmationParser.CANCEL_CLEAR_COMMAND);

        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonInternshipDiaryIoExceptionThrowingStub
        JsonInternshipDiaryStorage internshipDiaryStorage =
            new JsonInternshipDiaryIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionInternshipDiary.json"));
        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(internshipDiaryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + COMPANY_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + ROLE_DESC_AMY + DATE_DESC_AMY + PRIORITY_DESC_AMY + STATUS_DESC_AMY;
        InternshipApplication expectedInternshipApplication = new InternshipApplicationBuilder(AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addInternshipApplication(expectedInternshipApplication);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredInternshipApplicationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredInternshipApplicationList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
        Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
        String expectedMessage) {
        Model expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
        String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonInternshipDiaryIoExceptionThrowingStub extends JsonInternshipDiaryStorage {
        private JsonInternshipDiaryIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveInternshipDiary(ReadOnlyInternshipDiary internshipDiary, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

}
