package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;

/**
 * A ui for the statistics that is displayed at the footer of the application.
 */
public class Statistics extends UiPart<Region> {

    private static final String FXML = "Statistics.fxml";
    private final ObservableList<InternshipApplication> internshipApplicationList;

    private final double TOTAL_PERCENTAGE = 100;
    private final String TOTAL = "TOTAL";

    private int wishlistCount = 0;
    private int appliedCount = 0;
    private int interviewCount = 0;
    private int offeredCount = 0;
    private int rejectedCount = 0;
    private int totalCount = 0;

    private double wishlistPercentage = 0;
    private double appliedPercentage = 0;
    private double interviewPercentage = 0;
    private double offeredPercentage = 0;
    private double rejectedPercentage = 0;

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

    public Statistics(ObservableList<InternshipApplication> internshipApplicationList) {
        super(FXML);
        this.internshipApplicationList = internshipApplicationList;
        updateStatisticsOnChange(internshipApplicationList);
        bindUpdatedStatistics();

    }

    public void updateStatisticsOnChange(ObservableList<InternshipApplication> internshipApplicationList) {
        internshipApplicationList.addListener((ListChangeListener<InternshipApplication>) c -> {
            while (c.next()) {
                if (c.wasAdded() || c.wasRemoved() || c.wasUpdated() || c.wasReplaced()) {
                    bindUpdatedStatistics();
                }
            }
        });
    }

    public void bindUpdatedStatistics() {
        computeAndUpdateStatistics();
        wishlist.setText(String.format("%s: %d (%.2f%%)", Status.WISHLIST, wishlistCount, wishlistPercentage));
        applied.setText(String.format("%s: %d (%.2f%%)", Status.APPLIED, appliedCount, appliedPercentage));
        interview.setText(String.format("%s: %d (%.2f%%)", Status.INTERVIEW, interviewCount, interviewPercentage));
        offered.setText(String.format("%s: %d (%.2f%%)", Status.OFFERED, offeredCount, offeredPercentage));
        rejected.setText(String.format("%s: %d (%.2f%%)", Status.REJECTED, rejectedCount, rejectedPercentage));
        total.setText(String.format("%s: %d (%.2f%%)", TOTAL, totalCount, TOTAL_PERCENTAGE));
    }

    public void computeAndUpdateStatistics() {
        resetStatistics();
        for (int i = 0; i < this.internshipApplicationList.size(); i++) {
            InternshipApplication ia = this.internshipApplicationList.get(i);
            Status iaStatus = ia.getStatus();
            switch (iaStatus) {
            case WISHLIST:
                wishlistCount++;
                break;
            case APPLIED:
                appliedCount++;
                break;
            case INTERVIEW:
                interviewCount++;
                break;
            case OFFERED:
                offeredCount++;
                break;
            case REJECTED:
                rejectedCount++;
                break;
            }
        }
        this.totalCount = wishlistCount + appliedCount + interviewCount + offeredCount + rejectedCount;
        wishlistPercentage = ((double) wishlistCount / totalCount) * 100;
        appliedPercentage = ((double) appliedCount / totalCount) * 100;
        interviewPercentage = ((double) interviewCount / totalCount) * 100;
        offeredPercentage = ((double) offeredCount / totalCount) * 100;
        rejectedPercentage = ((double) rejectedCount / totalCount) * 100;
    }

    public void resetStatistics() {
        this.wishlistCount = 0;
        this.appliedCount = 0;
        this.interviewCount = 0;
        this.offeredCount = 0;
        this.rejectedCount = 0;
    }

    public int getWishlistCount() {
        return this.wishlistCount;
    }

    public int getAppliedCount() {
        return this.appliedCount;
    }

    public int getInterviewCount() {
        return this.interviewCount;
    }

    public int getOfferedCount() {
        return this.offeredCount;
    }

    public int getRejectedCount() {
        return this.rejectedCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public double getWishlistPercentage() {
        return this.wishlistPercentage;
    }

    public double getAppliedPercentage() {
        return this.appliedPercentage;
    }

    public double getInterviewPercentage() {
        return this.interviewPercentage;
    }

    public double getOfferedPercentage() {
        return this.offeredPercentage;
    }

    public double getRejectedPercentage() {
        return this.rejectedPercentage;
    }

}
