package seedu.diary.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.statistics.Statistics;
import seedu.diary.model.status.Status;

/**
 * A graphical interface for the statistics that is displayed at the footer of the application.
 */
public class StatisticsBarFooter extends UiPart<Region> implements PropertyChangeListener {

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
    private Label ghosted;
    @FXML
    private Label total;

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

    public StatisticsBarFooter(Statistics statistics, ObservableList<InternshipApplication> internshipApplicationList) {
        super(FXML);
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
     * Computes and updates the statistics for statistics bar footer.
     */
    public void updateStatistics() {
        statistics.computeAndUpdateStatistics(internshipApplicationList);
        int wishlistCount = statistics.getCount(Status.WISHLIST);
        int appliedCount = statistics.getCount(Status.APPLIED);
        int interviewCount = statistics.getCount(Status.INTERVIEW);
        int offeredCount = statistics.getCount(Status.OFFERED);
        int rejectedCount = statistics.getCount(Status.REJECTED);
        int ghostedCount = statistics.getCount(Status.GHOSTED);
        int totalCount = statistics.getTotalCount();
        bindStatistics(wishlistCount, appliedCount, interviewCount, offeredCount, rejectedCount, ghostedCount,
            totalCount);
    }

    /**
     * Binds the statistics to the user interface.
     *
     * @param wishlistCount number of internship application(s) in wishlist
     * @param appliedCount number of internship application(s) that has/ have been applied for
     * @param interviewCount number of internship application(s) that has/ have been scheduled for interview
     * @param offeredCount number of internship application(s) that has/ have been offered
     * @param rejectedCount number of internship application(s) that has/ have been rejected
     * @param ghostedCount number of internship application(s) that has/ have been ghosted
     * @param totalCount total number of internship applications in InternshipDiary
     */
    public void bindStatistics(int wishlistCount, int appliedCount, int interviewCount,
        int offeredCount, int rejectedCount, int ghostedCount, int totalCount) {
        wishlist.setText(String.format("%s: %d", Status.WISHLIST, wishlistCount));
        applied.setText(String.format("%s: %d", Status.APPLIED, appliedCount));
        interview.setText(String.format("%s: %d", Status.INTERVIEW, interviewCount));
        offered.setText(String.format("%s: %d", Status.OFFERED, offeredCount));
        rejected.setText(String.format("%s: %d", Status.REJECTED, rejectedCount));
        ghosted.setText(String.format("%s: %d", Status.GHOSTED, ghostedCount));
        total.setText(String.format("%s: %d", Statistics.TOTAL, totalCount));
    }

}
