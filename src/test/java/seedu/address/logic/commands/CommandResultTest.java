package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false)));

        // different showStatistics value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, true)));

        CommandResult commandResultWithInternshipApplication = new CommandResult("feedback",
            TypicalInternshipApplications.AMY);

        // different showInternshipApplication value -> returns false
        assertFalse(commandResult.equals(commandResultWithInternshipApplication));

        CommandResult commandResultWithDifferentInternshipApplication = new CommandResult("feedback",
            TypicalInternshipApplications.BOB);

        // different Internship Application -> returns false
        assertFalse(commandResultWithInternshipApplication.equals(commandResultWithDifferentInternshipApplication));

        CommandResult commandResultWithSameInternshipApplication = new CommandResult("feedback",
            TypicalInternshipApplications.AMY);

        // same Internship Application -> returns true
        assertTrue(commandResultWithInternshipApplication.equals(commandResultWithSameInternshipApplication));

    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false).hashCode());

        // different showStatistics value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, true).hashCode());
    }

}
