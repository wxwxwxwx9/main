package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ReminderCommand.MESSAGE_SUCCESS;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.comparator.ApplicationDateAndInterviewDateComparator;
import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;
import seedu.address.model.internship.predicate.ApplicationDateDuePredicate;
import seedu.address.model.internship.predicate.InterviewDateDuePredicate;
import seedu.address.model.internship.predicate.IsNotArchivedPredicate;
import seedu.address.model.internship.predicate.StatusIsInterviewPredicate;
import seedu.address.model.internship.predicate.StatusIsWishlistPredicate;
import seedu.address.model.status.Status;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.InterviewBuilder;

class ReminderCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        LocalDate currentDate = LocalDate.now();
        // old application which was rejected
        InternshipApplication applicationA = new InternshipApplicationBuilder()
                .withApplicationDate(new ApplicationDate(currentDate.minus(2, ChronoUnit.DAYS)))
                .withCompany("Company A")
                .withStatus(Status.REJECTED)
                .build();
        // application that has not been applied to yet, due in 7 days
        InternshipApplication applicationB = new InternshipApplicationBuilder()
                .withApplicationDate(new ApplicationDate(currentDate.plus(5, ChronoUnit.DAYS)))
                .withCompany("Company B")
                .withStatus(Status.WISHLIST)
                .build();
        // application that has not been applied to yet, application date already over
        InternshipApplication applicationC = new InternshipApplicationBuilder()
                .withApplicationDate(new ApplicationDate(currentDate.minus(10, ChronoUnit.DAYS)))
                .withCompany("Company C")
                .withStatus(Status.WISHLIST)
                .build();
        // application with Status.APPLIED, and has an interview in 7 days
        Interview interviewD = new InterviewBuilder()
                .withDate(currentDate.plus(2, ChronoUnit.DAYS))
                .build();
        InternshipApplication applicationD = new InternshipApplicationBuilder()
                .withApplicationDate(new ApplicationDate(currentDate.minus(1, ChronoUnit.DAYS)))
                .withCompany("Company D")
                .withStatus(Status.APPLIED)
                .withInterview(interviewD)
                .buildWithInterviews();
        // application with Status.INTERVIEW, and has an interview in 7 days
        Interview interviewE = new InterviewBuilder()
                .withDate(currentDate.plus(2, ChronoUnit.DAYS))
                .build();
        InternshipApplication applicationE = new InternshipApplicationBuilder()
                .withApplicationDate(new ApplicationDate(currentDate.minus(1, ChronoUnit.DAYS)))
                .withCompany("Company E")
                .withStatus(Status.INTERVIEW)
                .withInterview(interviewE)
                .buildWithInterviews();
        // application with Status.INTERVIEW, and does not have an interview in 7 days
        Interview interviewF = new InterviewBuilder()
                .withDate(currentDate.plus(9, ChronoUnit.DAYS))
                .build();
        InternshipApplication applicationF = new InternshipApplicationBuilder()
                .withApplicationDate(new ApplicationDate(currentDate.minus(3, ChronoUnit.DAYS)))
                .withCompany("Company F")
                .withStatus(Status.INTERVIEW)
                .withInterview(interviewF)
                .buildWithInterviews();
        InternshipDiary internshipDiary = new InternshipDiary();
        internshipDiary.loadInternshipApplication(applicationA);
        internshipDiary.loadInternshipApplication(applicationB);
        internshipDiary.loadInternshipApplication(applicationC);
        internshipDiary.loadInternshipApplication(applicationD);
        internshipDiary.loadInternshipApplication(applicationE);
        internshipDiary.loadInternshipApplication(applicationF);
        model = new ModelManager(internshipDiary, userPrefs);
        expectedModel = new ModelManager(model.getInternshipDiary(), userPrefs);
    }

    @Test
    public void execute_afterReminder_showsFilteredAndSortedList() {
        ApplicationDateDuePredicate appDateWithin7DaysPredicate = new ApplicationDateDuePredicate();
        StatusIsWishlistPredicate statusIsWishlistPredicate = new StatusIsWishlistPredicate();
        List<Predicate<InternshipApplication>> checkWishlistPredicate = new ArrayList<>();
        checkWishlistPredicate.add(appDateWithin7DaysPredicate);
        checkWishlistPredicate.add(statusIsWishlistPredicate);
        Predicate<InternshipApplication> wishlistPredicate = checkWishlistPredicate.stream()
                .reduce(x -> true, Predicate::and);

        InterviewDateDuePredicate interviewDateWithin7DaysPredicate = new InterviewDateDuePredicate();
        StatusIsInterviewPredicate statusIsInterviewPredicate = new StatusIsInterviewPredicate();
        List<Predicate<InternshipApplication>> checkInterviewPredicate = new ArrayList<>();
        checkInterviewPredicate.add(interviewDateWithin7DaysPredicate);
        checkInterviewPredicate.add(statusIsInterviewPredicate);
        Predicate<InternshipApplication> interviewPredicate = checkInterviewPredicate.stream()
                .reduce(x -> true, Predicate::and);

        List<Predicate<InternshipApplication>> checkDatePredicates = new ArrayList<>();
        checkDatePredicates.add(wishlistPredicate);
        checkDatePredicates.add(interviewPredicate);
        Predicate<InternshipApplication> datePredicate = checkDatePredicates.stream().reduce(x -> false, Predicate::or);

        IsNotArchivedPredicate isNotArchivedPredicate = new IsNotArchivedPredicate();
        List<Predicate<InternshipApplication>> dateAndNotArchivedPredicates = new ArrayList<>();
        dateAndNotArchivedPredicates.add(datePredicate);
        dateAndNotArchivedPredicates.add(isNotArchivedPredicate);
        Predicate<InternshipApplication> predicate = dateAndNotArchivedPredicates.stream()
                .reduce(x -> true, Predicate::and);

        assertEquals(expectedModel.getAllInternshipApplicationList().size(), 6);
        expectedModel.updateFilteredInternshipApplicationList(predicate);
        expectedModel.updateFilteredInternshipApplicationList(new ApplicationDateAndInterviewDateComparator());
        assertEquals(expectedModel.getFilteredInternshipApplicationList().size(), 2);
        CommandResult expectedMessage = new CommandResult(MESSAGE_SUCCESS);
        assertEquals(expectedMessage, new ReminderCommand().execute(model));
        assertCommandSuccess(new ReminderCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ReminderCommand firstReminder = new ReminderCommand();

        // same object -> returns true
        assertEquals(firstReminder, firstReminder);

        // different types -> returns false
        assertNotEquals("reminder", firstReminder);

        // null -> returns false
        assertNotEquals(null, firstReminder);
    }
}
