package seedu.diary.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for {@code EnteredCommandsHistory}.
 */
public class EnteredCommandsHistoryTest {

    @Test
    public void size_noSize_sizeCappedAt20() {
        EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory();
        assertEquals(commandsHistory.size(), 0);
        for (int i = 0; i < 19; i++) {
            commandsHistory.add("a");
        }
        assertEquals(commandsHistory.size(), 19);
        commandsHistory.add("a");
        assertEquals(commandsHistory.size(), 20);
        commandsHistory.add("a");
        assertEquals(commandsHistory.size(), 20);
    }

    @Test
    public void constructor_hasSize_sizeCapped() {
        int maxSize = 10;
        EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory(maxSize);
        assertEquals(commandsHistory.size(), 0);
        for (int i = 0; i < maxSize - 1; i++) {
            commandsHistory.add("a");
        }
        assertEquals(commandsHistory.size(), maxSize - 1);
        commandsHistory.add("a");
        assertEquals(commandsHistory.size(), maxSize);
        commandsHistory.add("a");
        assertEquals(commandsHistory.size(), maxSize);
    }

    @Test
    public void iterateNext_noNextHistory_returnNull() {
        EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory();
        assertNull(commandsHistory.iterateNext());
        commandsHistory.add("a");
        commandsHistory.iterateNext();
        assertNull(commandsHistory.iterateNext());
    }

    @Test
    public void iteratePrevious_noPreviousHistory_returnNullAndEmptyString() {
        EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory();
        assertNull(commandsHistory.iteratePrevious());
        commandsHistory.add("a");
        commandsHistory.iterateNext();
        commandsHistory.iteratePrevious();
        assertEquals(commandsHistory.iteratePrevious(), "");
    }

    @Test
    public void iterate_resetIterator_sameOrder() {
        EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory();
        commandsHistory.add("d");
        commandsHistory.add("c");
        commandsHistory.add("b");
        commandsHistory.add("a");
        assertEquals(commandsHistory.iterateNext(), "a");
        assertEquals(commandsHistory.iterateNext(), "b");
        assertEquals(commandsHistory.iterateNext(), "c");
        assertEquals(commandsHistory.iterateNext(), "d");
        assertEquals(commandsHistory.iteratePrevious(), "c");
        assertEquals(commandsHistory.iteratePrevious(), "b");
        commandsHistory.resetIterator();
        assertEquals(commandsHistory.iterateNext(), "a");
    }

    @Test
    public void resetIterator_iteratorNotNull_iteratePreviousNull() {
        EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory();
        commandsHistory.add("a");
        commandsHistory.iterateNext();
        commandsHistory.resetIterator();
        assertNull(commandsHistory.iteratePrevious());
    }

    @Test
    public void add_iteratorNotNull_resetIterator() {
        EnteredCommandsHistory commandsHistory = new EnteredCommandsHistory();
        commandsHistory.add("a");
        commandsHistory.iterateNext();
        commandsHistory.add("a");
        commandsHistory.iterateNext();
        assertNotNull(commandsHistory.iterateNext());
        assertNull(commandsHistory.iterateNext());
    }
}
