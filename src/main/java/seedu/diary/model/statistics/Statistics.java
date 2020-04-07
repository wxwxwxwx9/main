package seedu.diary.model.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.status.Status;

/**
 * Represents a statistics generator model.
 * Generates relevant statistics for internship applications.
 */
public class Statistics {
    public static final String TOTAL = "TOTAL";

    /**
     * Contains all Status enum constants.
     */
    private Status[] statuses = Status.class.getEnumConstants();

    /**
     * Stores mapping of each Status to their count across internship application(s).
     */
    private HashMap<Status, Integer> statusCount = new HashMap<>();

    public Statistics() {
        resetStatistics();
    }

    /**
     * Computes and updates the overall statistics based on the latest list of internship applications given.
     * It will reset any existing statistics before re-computing.
     *
     * @param internshipApplicationList list of existing internship application(s).
     */
    public void computeAndUpdateStatistics(ObservableList<InternshipApplication> internshipApplicationList) {
        resetStatistics();
        computeCount(internshipApplicationList);
    }

    /**
     * Computes and updates the count for each internship application status.
     *
     * @param internshipApplicationList list of existing internship application(s).
     */
    public void computeCount(ObservableList<InternshipApplication> internshipApplicationList) {
        List<Status> newStatuses = internshipApplicationList.stream()
            .map(ia -> {
                return ia.getStatus();
            })
            .collect(Collectors.toList());
        newStatuses.forEach((status) -> {
            int count = statusCount.get(status);
            statusCount.put(status, ++count);
        });
    }

    /**
     * Resets the current statistics.
     */
    public void resetStatistics() {
        for (Status status : statuses) {
            statusCount.put(status, 0);
        }
    }

    public int getCount(Status status) {
        return statusCount.get(status);
    }

    public int getTotalCount() {
        Iterator statusCountIterator = statusCount.entrySet().iterator();
        int totalCount = 0;
        while (statusCountIterator.hasNext()) {
            Map.Entry element = (Map.Entry) statusCountIterator.next();
            int count = (int) element.getValue();
            totalCount += count;
        }
        return totalCount;
    }

    public double getPercentage(Status status) {
        return ((double) statusCount.get(status) / getTotalCount()) * 100;
    }

    public Status[] getStatuses() {
        return this.statuses;
    }

}
