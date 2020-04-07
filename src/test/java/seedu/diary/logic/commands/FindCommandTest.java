package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.commons.core.Messages.MESSAGE_INTERNSHIP_LISTED_OVERVIEW;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.testutil.PredicateUtil.prepareAddressPredicate;
import static seedu.diary.testutil.PredicateUtil.prepareCompanyPredicate;
import static seedu.diary.testutil.PredicateUtil.prepareEmailPredicate;
import static seedu.diary.testutil.PredicateUtil.preparePhonePredicate;
import static seedu.diary.testutil.PredicateUtil.preparePriorityPredicate;
import static seedu.diary.testutil.PredicateUtil.prepareRolePredicate;
import static seedu.diary.testutil.PredicateUtil.prepareStatusPredicate;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.ApplicationDateIsDatePredicate;
import seedu.diary.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.StatusContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void equals() {
        CompanyContainsKeywordsPredicate cFirstPredicate =
            new CompanyContainsKeywordsPredicate(Collections.singletonList("first"));
        CompanyContainsKeywordsPredicate cSecondPredicate =
            new CompanyContainsKeywordsPredicate(Collections.singletonList("second"));
        RoleContainsKeywordsPredicate rFirstPredicate =
            new RoleContainsKeywordsPredicate(Collections.singletonList("first"));
        RoleContainsKeywordsPredicate rSecondPredicate =
            new RoleContainsKeywordsPredicate(Collections.singletonList("second"));
        AddressContainsKeywordsPredicate aFirstPredicate =
            new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate aSecondPredicate =
            new AddressContainsKeywordsPredicate(Collections.singletonList("second"));
        PhoneContainsNumbersPredicate pFirstPredicate =
            new PhoneContainsNumbersPredicate(Collections.singletonList("first"));
        PhoneContainsNumbersPredicate pSecondPredicate =
            new PhoneContainsNumbersPredicate(Collections.singletonList("second"));
        EmailContainsKeywordsPredicate eFirstPredicate =
            new EmailContainsKeywordsPredicate(Collections.singletonList("first"));
        EmailContainsKeywordsPredicate eSecondPredicate =
            new EmailContainsKeywordsPredicate(Collections.singletonList("second"));
        ApplicationDateIsDatePredicate dFirstPredicate =
            new ApplicationDateIsDatePredicate(LocalDate.of(2020, 02, 01));
        ApplicationDateIsDatePredicate dSecondPredicate =
            new ApplicationDateIsDatePredicate(LocalDate.of(2021, 03, 02));
        PriorityContainsNumbersPredicate wFirstPredicate =
            new PriorityContainsNumbersPredicate(Collections.singletonList("first"));
        PriorityContainsNumbersPredicate wSecondPredicate =
            new PriorityContainsNumbersPredicate(Collections.singletonList("second"));
        StatusContainsKeywordsPredicate sFirstPredicate =
            new StatusContainsKeywordsPredicate(Collections.singletonList("first"));
        StatusContainsKeywordsPredicate sSecondPredicate =
            new StatusContainsKeywordsPredicate(Collections.singletonList("second"));


        FindCommand findFirstCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), false);
        FindCommand findSecondCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), true);
        FindCommand findThirdCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sSecondPredicate), false);
        FindCommand findFourthCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wSecondPredicate, sFirstPredicate), false);
        FindCommand findFifthCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dSecondPredicate, wFirstPredicate, sFirstPredicate), false);
        FindCommand findSixthCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eSecondPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), false);
        FindCommand findSeventhCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pSecondPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), false);
        FindCommand findEighthCommand = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aSecondPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), false);
        FindCommand findNinthCommand = new FindCommand(List.of(cFirstPredicate, rSecondPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), false);
        FindCommand findTenthCommand = new FindCommand(List.of(cSecondPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), false);


        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(List.of(cFirstPredicate, rFirstPredicate, aFirstPredicate,
            pFirstPredicate, eFirstPredicate, dFirstPredicate, wFirstPredicate, sFirstPredicate), false);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different argument -> return false
        assertFalse(findFirstCommand.equals(findThirdCommand));
        assertFalse(findFirstCommand.equals(findFourthCommand));
        assertFalse(findFirstCommand.equals(findFifthCommand));
        assertFalse(findFirstCommand.equals(findSixthCommand));
        assertFalse(findFirstCommand.equals(findSeventhCommand));
        assertFalse(findFirstCommand.equals(findEighthCommand));
        assertFalse(findFirstCommand.equals(findNinthCommand));
        assertFalse(findFirstCommand.equals(findTenthCommand));
    }

    @Test
    public void execute_zeroKeywords_noInternshipApplicationFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 0);
        CompanyContainsKeywordsPredicate cPredicate = prepareCompanyPredicate(" ");
        RoleContainsKeywordsPredicate rPredicate = prepareRolePredicate(" ");
        AddressContainsKeywordsPredicate aPredicate = prepareAddressPredicate(" ");
        PhoneContainsNumbersPredicate pPredicate = preparePhonePredicate(" ");
        EmailContainsKeywordsPredicate ePredicate = prepareEmailPredicate(" ");
        ApplicationDateIsDatePredicate dPredicate = new ApplicationDateIsDatePredicate((LocalDate) null);
        PriorityContainsNumbersPredicate wPredicate = preparePriorityPredicate(" ");
        StatusContainsKeywordsPredicate sPredicate = prepareStatusPredicate(" ");

        FindCommand command = new FindCommand(List.of(cPredicate, rPredicate, aPredicate, pPredicate, ePredicate,
            dPredicate, wPredicate, sPredicate), false);
        expectedModel.updateFilteredInternshipApplicationList(cPredicate.and(rPredicate)
            .and(aPredicate)
            .and(pPredicate)
            .and(ePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredInternshipApplicationList());
    }

    @Test
    public void execute_multipleKeywords_multipleInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 2);
        CompanyContainsKeywordsPredicate cPredicate = prepareCompanyPredicate("google facebook");
        FindCommand command = new FindCommand(List.of(cPredicate, new RoleContainsKeywordsPredicate(null),
            new AddressContainsKeywordsPredicate(null), new PhoneContainsNumbersPredicate(null),
            new EmailContainsKeywordsPredicate(null), new ApplicationDateIsDatePredicate((LocalDate) null),
            new PriorityContainsNumbersPredicate(null), new StatusContainsKeywordsPredicate(null)),
            false);
        expectedModel.updateFilteredInternshipApplicationList(cPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_isPreamble_multipleInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 2);
        CompanyContainsKeywordsPredicate cPredicate = prepareCompanyPredicate("goo");
        RoleContainsKeywordsPredicate rPredicate = prepareRolePredicate("goo");
        AddressContainsKeywordsPredicate aPredicate = prepareAddressPredicate("goo");
        PhoneContainsNumbersPredicate pPredicate = preparePhonePredicate("goo");
        EmailContainsKeywordsPredicate ePredicate = prepareEmailPredicate("goo");
        PriorityContainsNumbersPredicate wPredicate = preparePriorityPredicate("goo");
        StatusContainsKeywordsPredicate sPredicate = prepareStatusPredicate("goo");

        FindCommand command = new FindCommand(List.of(cPredicate, rPredicate, aPredicate, pPredicate, ePredicate,
            wPredicate, sPredicate), true);
        Predicate<InternshipApplication> predicate =
            cPredicate.or(rPredicate).or(aPredicate).or(pPredicate).or(ePredicate).or(wPredicate).or(sPredicate);
        expectedModel.updateFilteredInternshipApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_isNotPreamble_multipleInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, 2);
        EmailContainsKeywordsPredicate ePredicate = prepareEmailPredicate("google");

        FindCommand command = new FindCommand(List.of(ePredicate), false);
        expectedModel.updateFilteredInternshipApplicationList(ePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

}
