package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;
import seedu.address.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code Statistics}.
 */
public class StatisticsTest {

    private static final InternshipApplication FACEBOOK = new InternshipApplicationBuilder()
            .withCompany("Facebook")
            .withStatus(Status.WISHLIST)
            .build();
    private static final InternshipApplication APPLE = new InternshipApplicationBuilder()
            .withCompany("Apple")
            .withStatus(Status.APPLIED)
            .build();
    private static final InternshipApplication AMAZON = new InternshipApplicationBuilder()
            .withCompany("Amazon")
            .withStatus(Status.INTERVIEW)
            .build();
    private static final InternshipApplication NETFLIX = new InternshipApplicationBuilder()
            .withCompany("Netflix")
            .withStatus(Status.OFFERED)
            .build();
    private static final InternshipApplication GOOGLE = new InternshipApplicationBuilder()
            .withCompany("Google")
            .withStatus(Status.REJECTED)
            .build();
    private static final InternshipApplication SHOPEE = new InternshipApplicationBuilder()
            .withCompany("Shopee")
            .withStatus(Status.GHOSTED)
            .build();

    private Statistics statistics;
    private Statistics expectedStatistics;
    private Model model;
    private Model emptyModel;

    private HashMap<Status, Integer> statusCount;

    private Status[] statuses;

    @BeforeEach
    public void setUp() {
        statistics = new Statistics();
        expectedStatistics = new Statistics();
        model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
        emptyModel = new ModelManager(new InternshipDiary(), new UserPrefs());
        statusCount = new HashMap<>();
        statuses = Status.class.getEnumConstants();
        initializeStatusCount();
    }

    @Test
    public void computeCount_internshipApplicationWithAllStatusAdded_countComputedCorrectly() {
        model.addInternshipApplication(FACEBOOK);
        model.addInternshipApplication(APPLE);
        model.addInternshipApplication(AMAZON);
        model.addInternshipApplication(NETFLIX);
        model.addInternshipApplication(GOOGLE);
        model.addInternshipApplication(SHOPEE);
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
        model.addInternshipApplication(FACEBOOK);
        model.addInternshipApplication(APPLE);
        model.addInternshipApplication(AMAZON);
        model.addInternshipApplication(NETFLIX);
        model.addInternshipApplication(GOOGLE);
        model.addInternshipApplication(SHOPEE);
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
