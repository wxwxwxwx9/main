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
        int wishlistCount = statistics.getWishlistCount();
        int appliedCount = statistics.getAppliedCount();
        int interviewCount = statistics.getInterviewCount();
        int offeredCount = statistics.getOfferedCount();
        int rejectedCount = statistics.getRejectedCount();
        assertNotEquals(wishlistCount, 0);
        assertNotEquals(appliedCount, 0);
        assertNotEquals(interviewCount, 0);
        assertNotEquals(offeredCount, 0);
        assertNotEquals(rejectedCount, 0);
    }

    @Test
    public void resetStatistics_allRelevantMetricsZero_success() {
        statistics.computeAndUpdateStatistics(model.getFilteredInternshipApplicationList());
        int wishlistCount = statistics.getWishlistCount();
        int appliedCount = statistics.getAppliedCount();
        int interviewCount = statistics.getInterviewCount();
        int offeredCount = statistics.getOfferedCount();
        int rejectedCount = statistics.getRejectedCount();
        assertNotEquals(wishlistCount, 0);
        assertNotEquals(appliedCount, 0);
        assertNotEquals(interviewCount, 0);
        assertNotEquals(offeredCount, 0);
        assertNotEquals(rejectedCount, 0);
        statistics.resetStatistics();
        wishlistCount = statistics.getWishlistCount();
        appliedCount = statistics.getAppliedCount();
        interviewCount = statistics.getInterviewCount();
        offeredCount = statistics.getOfferedCount();
        rejectedCount = statistics.getRejectedCount();
        assertEquals(wishlistCount, 0);
        assertEquals(appliedCount, 0);
        assertEquals(interviewCount, 0);
        assertEquals(offeredCount, 0);
        assertEquals(rejectedCount, 0);
    }

}
