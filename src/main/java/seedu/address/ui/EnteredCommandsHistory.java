package seedu.address.ui;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * The UI component helper that is responsible for storing the history of user command inputs.
 */
public class EnteredCommandsHistory {
    private final LinkedList<String> commandsHistory = new LinkedList<>();
    private ListIterator<String> historyIterator;


    public void add(String command) {
        this.commandsHistory.addFirst(command);
        if (this.commandsHistory.size() > 20) {
            this.commandsHistory.removeLast();
        }
        this.historyIterator = null;
    }

    public void resetIterator() {
        this.historyIterator = null;
    }

    public String iterateNext() {
        if (this.historyIterator == null) {
            this.historyIterator = commandsHistory.listIterator(0);
        }
        if (this.historyIterator.hasNext()) {
            return this.historyIterator.next();
        }
        return null;
    }

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
