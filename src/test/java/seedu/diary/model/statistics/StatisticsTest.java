package seedu.diary.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.diary.model.InternshipDiary;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.status.Status;
import seedu.diary.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code Statistics}.
 */
public class StatisticsTest {

    // these internship applications samples are currently only used within here
    // if other classes start needing such samples, we will create a new util class so that
    // we can reused all these samples
    private static final InternshipApplication WISHLIST_IA = new InternshipApplicationBuilder()
        .withCompany("a")
        .withStatus(Status.WISHLIST)
        .build();
    private static final InternshipApplication APPLIED_IA = new InternshipApplicationBuilder()
        .withCompany("b")
        .withStatus(Status.APPLIED)
        .build();
    private static final InternshipApplication INTERVIEW_IA = new InternshipApplicationBuilder()
        .withCompany("c")
        .withStatus(Status.INTERVIEW)
        .build();
    private static final InternshipApplication OFFERED_IA = new InternshipApplicationBuilder()
        .withCompany("d")
        .withStatus(Status.OFFERED)
        .build();
    private static final InternshipApplication REJECTED_IA = new InternshipApplicationBuilder()
        .withCompany("e")
        .withStatus(Status.REJECTED)
        .build();
    private static final InternshipApplication GHOSTED_IA = new InternshipApplicationBuilder()
        .withCompany("f")
        .withStatus(Status.GHOSTED)
        .build();

    private final Statistics statistics = new Statistics();
    private final Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    private final Model emptyModel = new ModelManager(new InternshipDiary(), new UserPrefs());

    private final HashMap<Status, Integer> statusCount = new HashMap<>();

    private final Status[] statuses = Status.class.getEnumConstants();

    @BeforeEach
    public void setUp() {
        model.addInternshipApplication(WISHLIST_IA);
        model.addInternshipApplication(APPLIED_IA);
        model.addInternshipApplication(INTERVIEW_IA);
        model.addInternshipApplication(OFFERED_IA);
        model.addInternshipApplication(REJECTED_IA);
        model.addInternshipApplication(GHOSTED_IA);
        initializeStatusCount();
    }

    @Test
    public void computeCount_internshipApplicationWithAllStatusAdded_countComputedCorrectly() {
        statistics.computeCount(model.getFilteredInternshipApplicationList());
        computeActualStatusCount();
        int actualTotalCount = 0;
        for (Status status : statuses) {
            int computedCount = statistics.getCount(status);
            int actualCount = statusCount.get(status);
            assertEquals(computedCount, actualCount);
            actualTotalCount += actualCount;
        }
        assertEquals(statistics.getTotalCount(), actualTotalCount);
    }

    @Test
    public void computeCount_noInternshipApplicationAdded_countRemainsZero() {
        statistics.computeCount(emptyModel.getFilteredInternshipApplicationList());
        for (Status status : statuses) {
            int computedCount = statistics.getCount(status);
            assertEquals(computedCount, 0);
        }
        assertEquals(statistics.getTotalCount(), 0);
    }

    @Test
    public void resetStatistics_internshipApplicationWithAllStatusAdded_countsBecomeZero() {
        statistics.computeAndUpdateStatistics(model.getFilteredInternshipApplicationList());
        for (Status status : statuses) {
            assertNotEquals(statistics.getCount(status), 0);
        }
        statistics.resetStatistics();
        for (Status status : statuses) {
            assertEquals(statistics.getCount(status), 0);
        }
    }

    private void initializeStatusCount() {
        for (Status status : statuses) {
            statusCount.put(status, 0);
        }
    }

    /**
     * Computes the actual count of statuses in internship application(s).
     */
    private void computeActualStatusCount() {
        List<Status> statuses = model.getFilteredInternshipApplicationList().stream()
            .map(ia -> {
                return ia.getStatus();
            })
            .collect(Collectors.toList());
        statuses.forEach((status) -> {
            int count = statusCount.get(status);
            statusCount.put(status, ++count);
        });
    }

}
