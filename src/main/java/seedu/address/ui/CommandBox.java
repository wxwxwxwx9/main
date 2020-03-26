package seedu.address.ui;

import java.util.LinkedList;
import java.util.ListIterator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private LinkedList<String> previousCommands = new LinkedList<>();
    private ListIterator<String> previousCommandIterator;

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
            previousCommands.addFirst(text);
            commandTextField.setText("");
            if (previousCommands.size() > 20) {
                previousCommands.removeLast();
            }
            previousCommandIterator = null;
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
            if (previousCommandIterator == null) {
                previousCommandIterator = previousCommands.listIterator(0);
            }
            if (previousCommandIterator.hasNext()) {
                commandTextField.setText(previousCommandIterator.next());
            }
        } else if (ke.getCode() == KeyCode.DOWN) {
            if (previousCommandIterator != null) {
                if (previousCommandIterator.hasPrevious()) {
                    previousCommandIterator.previous();
                }
                if (previousCommandIterator.hasPrevious()) {
                    commandTextField.setText(previousCommandIterator.previous());
                    previousCommandIterator.next();
                } else {
                    commandTextField.setText("");
                }
            }
        } else if (ke.getCode() != KeyCode.LEFT && ke.getCode() != KeyCode.RIGHT) {
            previousCommandIterator = null;
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
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
