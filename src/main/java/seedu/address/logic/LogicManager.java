package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.InternshipDiaryParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyInternshipDiary;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final InternshipDiaryParser internshipDiaryParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        internshipDiaryParser = new InternshipDiaryParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = internshipDiaryParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            //cf: Needs to be changed after storage is refactored.
            storage.saveInternshipDiary(model.getInternshipDiary());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyInternshipDiary getInternshipDiary() {
        return model.getInternshipDiary();
    }

    @Override
    public ObservableList<InternshipApplication> getFilteredInternshipApplicationList() {
        return model.getFilteredInternshipApplicationList();
    }

    @Override
    public Path getInternshipDiaryFilePath() {
        return model.getInternshipDiaryFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
