package seedu.diary.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import seedu.diary.logic.commands.CommandResult;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.logic.parser.exceptions.ClearCommandConfirmationParseException;
import seedu.diary.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory();

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String text = commandTextField.getText();
            commandExecutor.execute(text);
            commandsHistory.add(text);
            commandsHistory.resetIterator();
            commandTextField.setText("");
        } catch (ClearCommandConfirmationParseException e) {
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles any keyPressed pressed event.
     */
    @FXML
    private void handleKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.UP) {
            String text = commandsHistory.iterateNext();
            if (text != null) {
                commandTextField.setText(text);
                commandTextField.positionCaret(commandTextField.getLength());
            }
        } else if (ke.getCode() == KeyCode.DOWN) {
            String text = commandsHistory.iteratePrevious();
            if (text != null) {
                commandTextField.setText(text);
                commandTextField.positionCaret(commandTextField.getLength());
            }
        } else if (ke.getCode() != KeyCode.LEFT && ke.getCode() != KeyCode.RIGHT) {
            commandsHistory.resetIterator();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.diary.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
