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
 * Controller for the statistics page.
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
     * @param statistics statistics object that generates relevant statistics.
     * @param internshipApplicationList list of existing internship applications.
     */
    public StatisticsWindow(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        this(new Stage(), statistics, internshipApplicationList);
    }

    /**
     * Creates a new StatisticsWindow.
     *
     * @param root Stage to use as the root of the StatisticsWindow.
     */
    public StatisticsWindow(Stage root, Statistics statistics,
                            ObservableList<InternshipApplication> internshipApplicationList) {
        super(FXML, root);
        internshipApplicationChart.setLegendVisible(false);
        updateStatistics(statistics, internshipApplicationList);
        updateStatisticsOnChange(statistics, internshipApplicationList);
    }

    /**
     * Adds an event listener to update the statistics upon any changes in the given list of internship application.
     *
     * @param statistics statistics object that generates relevant statistics.
     * @param internshipApplicationList list of existing internship application(s).
     */
    public void updateStatisticsOnChange(Statistics statistics,
                                         ObservableList<InternshipApplication> internshipApplicationList) {
        internshipApplicationList.addListener((ListChangeListener<InternshipApplication>) c -> {
            while (c.next()) {
                if (c.wasAdded() || c.wasRemoved() || c.wasUpdated() || c.wasReplaced()) {
                    updateStatistics(statistics, internshipApplicationList);
                }
            }
        });
    }

    /**
     * Computes and updates the statistics on both bar chart and pie chart.
     *
     * @param statistics statistics object that generates relevant statistics.
     * @param internshipApplicationList list of existing internship application(s).
     */
    public void updateStatistics(Statistics statistics,
                                 ObservableList<InternshipApplication> internshipApplicationList) {
        statistics.computeAndUpdateStatistics(internshipApplicationList);
        loadBarChart(statistics);
        loadPieChart(statistics);
    }

    /**
     * Clears the existing data and loads the bar chart with new data.
     *
     * @param statistics statistics object that generates relevant statistics.
     */
    @SuppressWarnings("unchecked")
    public void loadBarChart(Statistics statistics) {
        internshipApplicationChart.getData().clear();
        ObservableList<XYChart.Data<String, Integer>> barChartData = generateBarChartData(statistics);
        internshipApplicationChart.getData().addAll(new XYChart.Series<String, Integer>(barChartData));
    }

    /**
     * Clears the existing data and loads the pie chart with new data.
     *
     * @param statistics statistics object that generates relevant statistics.
     */
    public void loadPieChart(Statistics statistics) {
        internshipApplicationPie.getData().clear();
        ObservableList<PieChart.Data> pieChartData = generatePieChartData(statistics);
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
     * Generates the relevant bar chart data using the generated count statistics.
     *
     * @param statistics statistics object that generates relevant statistics.
     */
    public ObservableList<XYChart.Data<String, Integer>> generateBarChartData(Statistics statistics) {
        ObservableList<XYChart.Data<String, Integer>> xyChartData = FXCollections.observableArrayList();
        for (Status status : statistics.getStatuses()) {
            XYChart.Data<String, Integer> data = new XYChart.Data<>(status.toString(), statistics.getCount(status));
            xyChartData.add(data);
        }
        return xyChartData;
    }

    /**
     * Generates the relevant pie chart data using the generated percentage statistics.
     *
     * @param statistics statistics object that generates relevant statistics.
     */
    public ObservableList<PieChart.Data> generatePieChartData(Statistics statistics) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Status status : statistics.getStatuses()) {
            PieChart.Data data = new PieChart.Data(status.toString(), statistics.getPercentage(status));
            pieChartData.add(data);
        }
        return pieChartData;
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
