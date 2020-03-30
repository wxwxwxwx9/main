package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private InternshipApplicationListPanel internshipApplicationListPanel;
    private ResultDisplay resultDisplay;
    private InternshipApplicationDetail internshipApplicationDetail;
    private HelpWindow helpWindow;
    private StatisticsWindow statisticsWindow;
    private StatisticsBarFooter statisticsBarFooter;
    private ComparatorDisplayFooter comparatorDisplayFooter;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane resultAndInternshipSplitPanePlaceholder;

    @FXML
    private SplitPane listAndDetailsSplitPanePlaceholder;

    @FXML
    private StackPane internshipApplicationListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane internshipApplicationDetailPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane statisticsPlaceholder;

    @FXML
    private StackPane comparatorDisplayPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        statisticsWindow = new StatisticsWindow(logic.getStatistics(), logic.getFilteredInternshipApplicationList());

        setSplitPaneDefaultSplit(0.2);
    }

    /**
     * Initializes the relevant UI objects to listen to model manager for property changes.
     */
    public void initListeners() {
        logic.addPropertyChangeListenerForModel(internshipApplicationListPanel);
        logic.addPropertyChangeListenerForModel(statisticsWindow);
        logic.addPropertyChangeListenerForModel(statisticsBarFooter);
        logic.addComparatorPropertyChangeListener(comparatorDisplayFooter);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the default split of the splitPane.
     */
    private void setSplitPaneDefaultSplit(double split) {
        this.primaryStage.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    resultAndInternshipSplitPanePlaceholder.setDividerPositions(split);
                    observable.removeListener(this);
                }
            }
        });
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        internshipApplicationListPanel = new InternshipApplicationListPanel(
                logic.getFilteredInternshipApplicationList());
        internshipApplicationListPanelPlaceholder.getChildren().add(internshipApplicationListPanel.getRoot());

        ListView<InternshipApplication> internshipApplicationListView = internshipApplicationListPanel
                .getInternshipApplicationListView();
        // Show internship application details on click
        internshipApplicationListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                internshipApplicationDetail = new InternshipApplicationDetail(internshipApplicationListView
                        .getSelectionModel().getSelectedItem());
                internshipApplicationDetailPlaceholder.getChildren().add(internshipApplicationDetail.getRoot());
            }
        });

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getInternshipDiaryFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        statisticsBarFooter = new StatisticsBarFooter(logic.getStatistics(),
                logic.getFilteredInternshipApplicationList());
        statisticsPlaceholder.getChildren().add(statisticsBarFooter.getRoot());

        comparatorDisplayFooter = new ComparatorDisplayFooter();
        comparatorDisplayPlaceholder.getChildren().add(comparatorDisplayFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the statistics window or focuses on it if it's already opened.
     */
    @FXML
    public void handleStatistics() {
        if (!statisticsWindow.isShowing()) {
            statisticsWindow.show();
        } else {
            statisticsWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        statisticsWindow.hide();
        primaryStage.hide();
    }

    public InternshipApplicationListPanel getInternshipApplicationListPanel() {
        return internshipApplicationListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowStatistics()) {
                handleStatistics();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
