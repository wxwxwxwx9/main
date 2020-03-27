package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.status.Status;

/**
 * A graphical interface for the statistics that is displayed at the footer of the application.
 */
public class StatisticsBarFooter extends UiPart<Region> {

    private static final String FXML = "StatisticsBarFooter.fxml";

    @FXML
    private Label wishlist;
    @FXML
    private Label applied;
    @FXML
    private Label interview;
    @FXML
    private Label offered;
    @FXML
    private Label rejected;
    @FXML
    private Label total;

    public StatisticsBarFooter(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        super(FXML);
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
     * Computes and updates the statistics for statistics bar footer.
     *
     * @param statistics statistics object that generates relevant statistics.
     * @param internshipApplicationList list of existing internship application(s).
     */
    public void updateStatistics(Statistics statistics,
                                         ObservableList<InternshipApplication> internshipApplicationList) {
        statistics.computeAndUpdateStatistics(internshipApplicationList);
        int wishlistCount = statistics.getCount(Status.WISHLIST);
        int appliedCount = statistics.getCount(Status.APPLIED);
        int interviewCount = statistics.getCount(Status.INTERVIEW);
        int offeredCount = statistics.getCount(Status.OFFERED);
        int rejectedCount = statistics.getCount(Status.REJECTED);
        int totalCount = statistics.getTotalCount();
        bindStatistics(wishlistCount, appliedCount, interviewCount, offeredCount, rejectedCount, totalCount);
    }

    /**
     * Binds the statistics to the user interface.
     *
     * @param wishlistCount
     * @param appliedCount
     * @param interviewCount
     * @param offeredCount
     * @param rejectedCount
     * @param totalCount
     */
    public void bindStatistics(int wishlistCount, int appliedCount, int interviewCount,
                               int offeredCount, int rejectedCount, int totalCount) {
        wishlist.setText(String.format("%s: %d", Status.WISHLIST, wishlistCount));
        applied.setText(String.format("%s: %d", Status.APPLIED, appliedCount));
        interview.setText(String.format("%s: %d", Status.INTERVIEW, interviewCount));
        offered.setText(String.format("%s: %d", Status.OFFERED, offeredCount));
        rejected.setText(String.format("%s: %d", Status.REJECTED, rejectedCount));
        total.setText(String.format("%s: %d", Statistics.TOTAL, totalCount));
    }

}
