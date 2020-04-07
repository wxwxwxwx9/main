package seedu.diary.ui;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * The UI component helper that is responsible for storing the history of user command inputs.
 */
public class EnteredCommandsHistory {
    private final LinkedList<String> commandsHistory = new LinkedList<>();
    private ListIterator<String> historyIterator;
    private final int maxSize;

    /**
     * Uses the default {@code maxSize} of 20 for history.
     */
    public EnteredCommandsHistory() {
        this.maxSize = 20;
    }

    /**
     * Uses the given {@code maxSize} for the history.
     */
    public EnteredCommandsHistory(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Adds string to history.
     *
     * @param command The string to be stored in the history.
     */
    public void add(String command) {
        this.commandsHistory.addFirst(command);
        if (this.commandsHistory.size() > maxSize) {
            this.commandsHistory.removeLast();
        }
        this.historyIterator = null;
    }

    /**
     * Returns the size of the command history.
     *
     * @return the size of the command history.
     */
    public int size() {
        return commandsHistory.size();
    }

    /**
     * Resets the history iterator.
     */
    public void resetIterator() {
        this.historyIterator = null;
    }

    /**
     * Returns a command earlier in history.
     *
     * @return the string stored earlier in history.
     */
    public String iterateNext() {
        if (this.historyIterator == null) {
            this.historyIterator = commandsHistory.listIterator(0);
        }
        if (this.historyIterator.hasNext()) {
            return this.historyIterator.next();
        }
        return null;
    }

    /**
     * Returns a command later in history.
     *
     * @return the string stored later in history.
     */
    public String iteratePrevious() {
        if (this.historyIterator != null) {
            if (this.historyIterator.hasPrevious()) {
                this.historyIterator.previous();
            }
            if (this.historyIterator.hasPrevious()) {
                String result = this.historyIterator.previous();
                this.historyIterator.next();
                return result;
            } else {
                return "";
            }
        }
        return null;
    }
}
