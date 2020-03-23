package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.status.Status;

/**
 * A ui for the statistics window page.
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
     *
     * @param statistics
     * @param internshipApplicationList
     */
    public StatisticsWindow(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        this(new Stage(), statistics, internshipApplicationList);
    }

    /**
     * Adds an event listener to update the statistics upon any changes in the given list of internship application.
     *
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
     *
     * @param statistics
     * @param internshipApplicationList
     */
    public void bindStatistics(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        statistics.computeAndUpdateStatistics(internshipApplicationList);
        loadBarChart(statistics);
        loadPieChart(statistics);
    }

    /**
     * Loads bar chart with the generated statistics.
     *
     * @param statistics
     */
    public void loadBarChart(Statistics statistics) {
        internshipApplicationChart.getData().clear();
        ObservableList<XYChart.Data> xyChartData = FXCollections.observableArrayList(
                new XYChart.Data(Status.WISHLIST.toString(), statistics.getCount(Status.WISHLIST)),
                new XYChart.Data(Status.APPLIED.toString(), statistics.getCount(Status.APPLIED)),
                new XYChart.Data(Status.INTERVIEW.toString(), statistics.getCount(Status.INTERVIEW)),
                new XYChart.Data(Status.OFFERED.toString(), statistics.getCount(Status.OFFERED)),
                new XYChart.Data(Status.REJECTED.toString(), statistics.getCount(Status.REJECTED))
        );

        ObservableList<XYChart.Series<String, Integer>> series = FXCollections.observableArrayList(
                new XYChart.Series(xyChartData)
        );

        internshipApplicationChart.setLegendVisible(false);
        internshipApplicationChart.getData().addAll(series);
    }

    /**
     * Loads pie chart with the generated statistics.
     *
     * @param statistics
     */
    public void loadPieChart(Statistics statistics) {
        internshipApplicationPie.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(Status.WISHLIST.toString(), statistics.getPercentage(Status.WISHLIST)),
                new PieChart.Data(Status.APPLIED.toString(), statistics.getPercentage(Status.APPLIED)),
                new PieChart.Data(Status.INTERVIEW.toString(), statistics.getPercentage(Status.INTERVIEW)),
                new PieChart.Data(Status.OFFERED.toString(), statistics.getPercentage(Status.OFFERED)),
                new PieChart.Data(Status.REJECTED.toString(), statistics.getPercentage(Status.REJECTED))
        );
        internshipApplicationPie.getData().addAll(pieChartData);
        pieChartData.forEach(data -> {
                // tooltip not working for some reason
                // Tooltip tip = new Tooltip(String.format("%.2f", data.getPieValue()));
                // Tooltip.install(data.getNode(), tip);
                data.nameProperty().bind(
                    Bindings.concat(String.format("%s (%.2f%%)", data.getName(), data.getPieValue()))
                );
            }
        );
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
