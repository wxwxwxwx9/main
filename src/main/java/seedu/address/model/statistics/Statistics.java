package seedu.address.model.statistics;

import javafx.collections.ObservableList;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;

/**
 * Represents a statistics generator model.
 * Generates relevant statistics for internship applications.
 */
public class Statistics {
    public static final String TOTAL = "TOTAL";
    public static final double TOTAL_PERCENTAGE = 100;

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

    /**
     * Computes and updates the overall statistics based on the list of internship applications given.
     * @param internshipApplicationList
     */
    public void computeAndUpdateStatistics(ObservableList<InternshipApplication> internshipApplicationList) {
        resetStatistics();
        computeCount(internshipApplicationList);
        computePercentage();
    }

    /**
     * Computes and updates the count for each internship application status.
     * @param internshipApplicationList
     */
    public void computeCount(ObservableList<InternshipApplication> internshipApplicationList) {
        for (int i = 0; i < internshipApplicationList.size(); i++) {
            InternshipApplication ia = internshipApplicationList.get(i);
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
            default:
            }
        }
    }

    /**
     * Computes and updates the percentage for each internship application status.
     */
    public void computePercentage() {
        this.totalCount = wishlistCount + appliedCount + interviewCount + offeredCount + rejectedCount;
        wishlistPercentage = ((double) wishlistCount / totalCount) * 100;
        appliedPercentage = ((double) appliedCount / totalCount) * 100;
        interviewPercentage = ((double) interviewCount / totalCount) * 100;
        offeredPercentage = ((double) offeredCount / totalCount) * 100;
        rejectedPercentage = ((double) rejectedCount / totalCount) * 100;
    }

    /**
     * Resets the current statistics.
     */
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
