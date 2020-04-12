package seedu.diary.model.internship.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.Test;

import seedu.diary.logic.commands.ArchiveCommand;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.testutil.InternshipApplicationBuilder;

class IsNotArchivedPredicateTest {

    @Test
    public void equals() {
        IsNotArchivedPredicate predicate = new IsNotArchivedPredicate();

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertNotEquals(null, predicate);
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
