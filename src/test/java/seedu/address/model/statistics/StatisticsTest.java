package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;
import seedu.address.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code Statistics}.
 */
public class StatisticsTest {

    public static final InternshipApplication APPLE = new InternshipApplicationBuilder().withCompany("Apple")
            .withRole("Software Engineer").withAddress("1600 Amphitheatre Parkway")
            .withPhone("94351253").withEmail("larry@google.com")
            .withStatus(Status.WISHLIST)
            .withApplicationDate(new ApplicationDate(LocalDate.of(2019, 12, 1)))
            .withPriority(10)
            .build();
    public static final InternshipApplication TWITTER = new InternshipApplicationBuilder().withCompany("Twitter")
            .withRole("Product Management").withAddress("1 Hacker Way, Menlo Park")
            .withPhone("99751354").withEmail("mark@google.com")
            .withStatus(Status.OFFERED)
            .withApplicationDate(new ApplicationDate(LocalDate.of(2009, 11, 11)))
            .withPriority(8)
            .build();
    public static final InternshipApplication NETFLIX = new InternshipApplicationBuilder().withCompany("Netflix")
            .withRole("Product Management").withAddress("1 Hacker Way, Menlo Park")
            .withPhone("99751354").withEmail("mark@google.com")
            .withStatus(Status.INTERVIEW)
            .withApplicationDate(new ApplicationDate(LocalDate.of(2009, 11, 11)))
            .withPriority(8)
            .build();

    private Statistics statistics;
    private Statistics expectedStatistics;
    private Model model;

    @BeforeEach
    public void setUp() {
        statistics = new Statistics();
        expectedStatistics = new Statistics();
        model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
        model.addInternshipApplication(APPLE);
        model.addInternshipApplication(TWITTER);
        model.addInternshipApplication(NETFLIX);
    }

    @Test
    public void computeAndUpdateStatistics_updateStatistics_success() {
        statistics.computeAndUpdateStatistics(model.getFilteredInternshipApplicationList());
        int wishlistCount = statistics.getCount(Status.WISHLIST);
        int appliedCount = statistics.getCount(Status.INTERVIEW);
        int interviewCount = statistics.getCount(Status.INTERVIEW);
        int offeredCount = statistics.getCount(Status.OFFERED);
        int rejectedCount = statistics.getCount(Status.REJECTED);
        int totalCount = statistics.getTotalCount();
        assertNotEquals(wishlistCount, 0);
        assertNotEquals(appliedCount, 0);
        assertNotEquals(interviewCount, 0);
        assertNotEquals(offeredCount, 0);
        assertNotEquals(rejectedCount, 0);
        assertEquals(totalCount, wishlistCount + appliedCount + interviewCount + offeredCount + rejectedCount);
    }

    @Test
    public void resetStatistics_allStatusCountsZero_success() {
        statistics.computeAndUpdateStatistics(model.getFilteredInternshipApplicationList());
        assertNotEquals(statistics.getCount(Status.WISHLIST), 0);
        assertNotEquals(statistics.getCount(Status.APPLIED), 0);
        assertNotEquals(statistics.getCount(Status.INTERVIEW), 0);
        assertNotEquals(statistics.getCount(Status.OFFERED), 0);
        assertNotEquals(statistics.getCount(Status.REJECTED), 0);
        statistics.resetStatistics();
        assertEquals(statistics.getCount(Status.WISHLIST), 0);
        assertEquals(statistics.getCount(Status.APPLIED), 0);
        assertEquals(statistics.getCount(Status.INTERVIEW), 0);
        assertEquals(statistics.getCount(Status.OFFERED), 0);
        assertEquals(statistics.getCount(Status.REJECTED), 0);
    }

}
