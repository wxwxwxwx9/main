package seedu.diary.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

import seedu.diary.commons.core.LogsCenter;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.statistics.Statistics;
import seedu.diary.model.status.Status;

/**
 * Controller for the statistics page.
 */
public class StatisticsWindow extends UiPart<Stage> implements PropertyChangeListener {

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

    private Statistics statistics;
    private ObservableList<InternshipApplication> internshipApplicationList;

    /**
     * To attach event listener to update statistics if there is any changes in the list.
     */
    private ListChangeListener<InternshipApplication> c = c -> {
        while (c.next()) {
            if (c.wasAdded() || c.wasRemoved() || c.wasUpdated() || c.wasReplaced()) {
                updateStatistics();
            }
        }
    };

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
     * @param statistics statistics object that generates relevant statistics.
     * @param internshipApplicationList list of existing internship applications.
     */
    public StatisticsWindow(Stage root, Statistics statistics,
        ObservableList<InternshipApplication> internshipApplicationList) {
        super(FXML, root);
        internshipApplicationChart.setLegendVisible(false);
        this.statistics = statistics;
        this.internshipApplicationList = internshipApplicationList;
        updateStatistics();
        updateStatisticsOnChange();
    }

    /**
     * Receives the latest changes in displayed internships from internship diary.
     * Reattaches listener to the latest internship application list and updates the relevant statistics accordingly.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        ObservableList<InternshipApplication> ia = (ObservableList<InternshipApplication>) e.getNewValue();
        this.internshipApplicationList.removeListener(c);
        internshipApplicationList = ia;
        updateStatistics();
        updateStatisticsOnChange();
    }

    /**
     * Adds an event listener to update the statistics upon any changes in the given list of internship application.
     */
    public void updateStatisticsOnChange() {
        internshipApplicationList.addListener(c);
    }

    /**
     * Computes and retrieves the latest statistics
     */
    public void updateStatistics() {
        statistics.computeAndUpdateStatistics(internshipApplicationList);
        loadBarChart();
        loadPieChart();
    }

    /**
     * Clears the existing data and loads the bar chart with new data.
     */
    @SuppressWarnings("unchecked")
    private void loadBarChart() {
        internshipApplicationChart.getData().clear();
        ObservableList<XYChart.Data<String, Integer>> barChartData = generateBarChartData();
        internshipApplicationChart.getData().addAll(new XYChart.Series<String, Integer>(barChartData));
    }

    /**
     * Clears the existing data and loads the pie chart with new data.
     */
    private void loadPieChart() {
        internshipApplicationPie.getData().clear();
        ObservableList<PieChart.Data> pieChartData = generatePieChartData();
        internshipApplicationPie.getData().addAll(pieChartData);
        bindPieChartLegend(pieChartData);
    }

    /**
     * Loads the legend of the pie chart with the percentage information if the percentage is valid.
     */
    private void bindPieChartLegend(ObservableList<PieChart.Data> pieChartData) {
        for (PieChart.Data data : pieChartData) {
            if (!Double.isNaN(data.getPieValue())) {
                String percentageLegend = String.format("%s (%.2f%%)", data.getName(), data.getPieValue());
                data.nameProperty().bind(
                    Bindings.concat(percentageLegend)
                );
            }
        }
    }

    /**
     * Generates the relevant bar chart data using the generated count statistics.
     */
    private ObservableList<XYChart.Data<String, Integer>> generateBarChartData() {
        ObservableList<XYChart.Data<String, Integer>> xyChartData = FXCollections.observableArrayList();
        for (Status status : statistics.getStatuses()) {
            XYChart.Data<String, Integer> data = new XYChart.Data<>(status.toString(), statistics.getCount(status));
            xyChartData.add(data);
        }
        return xyChartData;
    }

    /**
     * Generates the relevant pie chart data using the generated percentage statistics.
     */
    private ObservableList<PieChart.Data> generatePieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Status status : statistics.getStatuses()) {
            PieChart.Data data = new PieChart.Data(status.toString(), statistics.getPercentage(status));
            pieChartData.add(data);
        }
        return pieChartData;
    }

    /**
     * Shows the statistics window.
     *
     * @throws IllegalStateException <ul>
     * <li>
     * if this method is called on a thread other than the JavaFX Application Thread.
     * </li>
     * <li>
     * if this method is called during animation or layout processing.
     * </li>
     * <li>
     * if this method is called on the primary stage.
     * </li>
     * <li>
     * if {@code dialogStage} is already showing.
     * </li>
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
