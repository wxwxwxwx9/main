package seedu.address.model.internship.predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;
import seedu.address.testutil.InternshipApplicationBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

class IsNotArchivedPredicateTest {

    @Test
    public void equals() {
        IsNotArchivedPredicate predicate = new IsNotArchivedPredicate();

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_applicationNotArchived_returnsTrue() {
        IsNotArchivedPredicate predicate = new IsNotArchivedPredicate();

        assertTrue(predicate.test(new InternshipApplicationBuilder().build()));
    }

    @Test
    public void test_applicationIsArchived_returnsFalse() {
        UserPrefs userPrefs = new UserPrefs();
        Model model = new ModelManager(getTypicalInternshipDiary(), userPrefs);

        IsNotArchivedPredicate predicate = new IsNotArchivedPredicate();

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION);

        try {
            archiveCommand.execute(model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        InternshipApplication archivedInternshipApplication =
                model.getAllInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        assertFalse(predicate.test(archivedInternshipApplication));
    }
}