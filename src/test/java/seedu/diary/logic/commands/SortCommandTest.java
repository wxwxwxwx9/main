package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.diary.commons.core.Messages.MESSAGE_INTERNSHIP_LISTED_OVERVIEW;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.logic.comparator.ApplicationDateComparator;
import seedu.diary.logic.comparator.CompanyComparator;
import seedu.diary.logic.comparator.PriorityComparator;
import seedu.diary.logic.comparator.StatusComparator;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    private List<Comparator<InternshipApplication>> comparators = Arrays.asList(
        new CompanyComparator(),
        new ApplicationDateComparator(),
        new PriorityComparator(),
        new StatusComparator()
    );

    @Test
    public void equals() {
        Comparator<InternshipApplication> companyComparator = new CompanyComparator();
        Comparator<InternshipApplication> dateComparator = new ApplicationDateComparator();

        SortCommand sortCompanyCommand = new SortCommand(companyComparator);
        SortCommand sortDateCommand = new SortCommand(dateComparator);

        // same object -> returns true
        assertEquals(sortCompanyCommand, sortCompanyCommand);

        // same values -> returns true
        SortCommand sortCompanyCommandCopy = new SortCommand(companyComparator);
        assertEquals(sortCompanyCommand, sortCompanyCommandCopy);

        // different types -> returns false
        assertNotEquals(1, sortCompanyCommand);

        // null -> returns false
        assertNotEquals(null, sortCompanyCommand);

        // different company -> returns false
        assertNotEquals(sortCompanyCommand, sortDateCommand);
    }

    @Test
    public void execute_anyComparator_sameNumberOfInternshipApplicationFound() {
        int initialModelSize = model.getFilteredInternshipApplicationList().size();
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_LISTED_OVERVIEW, initialModelSize);
        for (Comparator<InternshipApplication> comparator : comparators) {
            SortCommand command = new SortCommand(comparator);
            expectedModel.updateFilteredInternshipApplicationList(comparator);
            assertEquals(model, expectedModel);
            assertCommandSuccess(command, model, expectedMessage, expectedModel);
        }
    }
}
