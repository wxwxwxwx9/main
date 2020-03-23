package seedu.address.model.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;

/**
 * Represents a statistics generator model.
 * Generates relevant statistics for internship applications.
 */
public class Statistics {
    public static final String TOTAL = "TOTAL";

    private Status[] statuses = Status.class.getEnumConstants();
    private HashMap<Status, Integer> statusCount = new HashMap<>();

    /**
     * Computes and updates the overall statistics based on the list of internship applications given.
     * @param internshipApplicationList
     */
    public void computeAndUpdateStatistics(ObservableList<InternshipApplication> internshipApplicationList) {
        resetStatistics();
        computeCount(internshipApplicationList);
    }

    /**
     * Computes and updates the count for each internship application status.
     * @param internshipApplicationList
     */
    public void computeCount(ObservableList<InternshipApplication> internshipApplicationList) {
        List<Status> newStatuses = internshipApplicationList.stream()
                .map(ia -> {
                    return ia.getStatus();
                })
                .collect(Collectors.toList());
        for (Status status : newStatuses) {
            int count = statusCount.get(status);
            statusCount.put(status, ++count);
        }
    }

    /**
     * Resets the current statistics.
     */
    public void resetStatistics() {
        for (Status status : statuses) {
            statusCount.put(status, 0);
        }
    }

    public int getWishlistCount() {
        return statusCount.get(Status.WISHLIST);
    }

    public int getAppliedCount() {
        return statusCount.get(Status.APPLIED);
    }

    public int getInterviewCount() {
        return statusCount.get(Status.INTERVIEW);
    }

    public int getOfferedCount() {
        return statusCount.get(Status.OFFERED);
    }

    public int getRejectedCount() {
        return statusCount.get(Status.REJECTED);
    }

    public int getTotalCount() {
        return getWishlistCount() + getAppliedCount() + getInterviewCount() + getOfferedCount() + getRejectedCount();
    }

    public double getWishlistPercentage() {
        return ((double) getWishlistCount() / getTotalCount()) * 100;
    }

    public double getAppliedPercentage() {
        return ((double) getAppliedCount() / getTotalCount()) * 100;
    }

    public double getInterviewPercentage() {
        return ((double) getInterviewCount() / getTotalCount()) * 100;
    }

    public double getOfferedPercentage() {
        return ((double) getOfferedCount() / getTotalCount()) * 100;
    }

    public double getRejectedPercentage() {
        return ((double) getRejectedCount() / getTotalCount()) * 100;
    }

}
