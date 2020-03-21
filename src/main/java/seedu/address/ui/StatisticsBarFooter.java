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
 * A ui for the statistics that is displayed at the footer of the application.
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
        bindStatistics(statistics, internshipApplicationList);
        updateStatisticsOnChange(statistics, internshipApplicationList);
    }

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

    public void bindStatistics(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        statistics.computeAndUpdateStatistics(internshipApplicationList);
        int wishlistCount = statistics.getWishlistCount();
        int appliedCount = statistics.getAppliedCount();
        int interviewCount = statistics.getInterviewCount();
        int offeredCount = statistics.getOfferedCount();
        int rejectedCount = statistics.getRejectedCount();
        int totalCount = statistics.getTotalCount();
        double wishlistPercentage = statistics.getWishlistPercentage();
        double appliedPercentage = statistics.getAppliedPercentage();
        double offeredPercentage = statistics.getOfferedPercentage();
        double interviewPercentage = statistics.getInterviewPercentage();
        double rejectedPercentage = statistics.getRejectedPercentage();
        wishlist.setText(String.format("%s: %d (%.2f%%)", Status.WISHLIST, wishlistCount, wishlistPercentage));
        applied.setText(String.format("%s: %d (%.2f%%)", Status.APPLIED, appliedCount, appliedPercentage));
        interview.setText(String.format("%s: %d (%.2f%%)", Status.INTERVIEW, interviewCount, interviewPercentage));
        offered.setText(String.format("%s: %d (%.2f%%)", Status.OFFERED, offeredCount, offeredPercentage));
        rejected.setText(String.format("%s: %d (%.2f%%)", Status.REJECTED, rejectedCount, rejectedPercentage));
        total.setText(String.format("%s: %d (%.2f%%)", Statistics.TOTAL, totalCount, Statistics.TOTAL_PERCENTAGE));
    }

}
