package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.status.Status;

import java.util.logging.Logger;

/**
 * Controller for the statistics page
 */
public class StatisticsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatisticsWindow.class);
    private static final String FXML = "StatisticsWindow.fxml";

    @FXML
    private BarChart<String, Integer> internshipApplicationChart;
    @FXML
    private CategoryAxis status;
    @FXML
    private NumberAxis count;
    @FXML
    private PieChart internshipApplicationPie;

    /**
     * Creates a new StatisticsWindow.
     *
     * @param root Stage to use as the root of the StatisticsWindow.
     */
    public StatisticsWindow(Stage root, Statistics statistics,
                            ObservableList<InternshipApplication> internshipApplicationList) {
        super(FXML, root);
        bindStatistics(statistics, internshipApplicationList);
        updateStatisticsOnChange(statistics, internshipApplicationList);
    }

    /**
     * Creates a new StatisticsWindow.
     */
    public StatisticsWindow(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        this(new Stage(), statistics, internshipApplicationList);
    }

    /**
     * Adds an event listener to update the statistics upon any changes in the given list of internship application.
     * @param statistics
     * @param internshipApplicationList
     */
    public void updateStatisticsOnChange(Statistics statistics,
                                         ObservableList<InternshipApplication> internshipApplicationList) {
        internshipApplicationList.addListener((ListChangeListener<InternshipApplication>) c -> {
            while (c.next()) {
                if (c.wasAdded() || c.wasRemoved() || c.wasUpdated() || c.wasReplaced()) {
                    bindStatistics(statistics, internshipApplicationList);
                }
            }
        });
    }

    /**
     * Computes and binds the statistics to the user interface.
     * @param statistics
     * @param internshipApplicationList
     */
    public void bindStatistics(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        // issue: color changes, and there is transition upon re-rendering
        // one solution can be to generate new Xaxis, Yaxis, and barchart each time, instead of reusing
        internshipApplicationChart.getData().clear();
        statistics.computeAndUpdateStatistics(internshipApplicationList);
//        status.setLabel("Status");
//        count.setLabel("Count");
        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>(Status.WISHLIST.toString(), statistics.getWishlistCount()));
        series.getData().add(new XYChart.Data<>(Status.APPLIED.toString(), statistics.getAppliedCount()));
        series.getData().add(new XYChart.Data<>(Status.INTERVIEW.toString(), statistics.getInterviewCount()));
        series.getData().add(new XYChart.Data<>(Status.OFFERED.toString(), statistics.getOfferedCount()));
        series.getData().add(new XYChart.Data<>(Status.REJECTED.toString(), statistics.getRejectedCount()));
        internshipApplicationChart.getData().add(series);
    }

    /**
     * Shows the statistics window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Generating statistics about your internship applications.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the statistics window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the statistics window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
