package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ReminderCommandTest {
    @Test
    void equals() {
        ReminderCommand firstReminder = new ReminderCommand();

        // same object -> returns true
        assertEquals(firstReminder, firstReminder);

        // different types -> returns false
        assertNotEquals("reminder", firstReminder);

        // null -> returns false
        assertNotEquals(null, firstReminder);
    }
}
